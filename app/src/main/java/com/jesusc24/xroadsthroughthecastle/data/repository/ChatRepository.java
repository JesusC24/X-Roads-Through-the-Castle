package com.jesusc24.xroadsthroughthecastle.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jesusc24.xroadsthroughthecastle.data.XRTCDatabase;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.dao.ChatDAO;
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryManageCallback;
import com.jesusc24.xroadsthroughthecastle.ui.foro.ChatListContract;
import com.jesusc24.xroadsthroughthecastle.ui.foro.ChatManagerContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChatRepository implements ChatListContract.Repository, ChatManagerContract.Repository {
    public static ChatRepository instance;
    private List<Chat> list;
    private ChatDAO chatDAO;

    private ChatRepository() {
        list = new ArrayList<>();
        chatDAO = XRTCDatabase.getDatabase().chatDAO();
    }

    public static ChatRepository getInstance() {
        if(instance == null) {
            instance = new ChatRepository();
        }

        return instance;
    }

    @Override
    public void getList(OnRepositoryListCallback callback) {
        list = new ArrayList<>();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_FORO)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null) {
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            Chat chat = new Chat();
                            chat.setNombre(queryDocumentSnapshot.getString(Constants.KEY_NAME));
                            chat.setDescripcion(queryDocumentSnapshot.getString(Constants.KEY_DESCRIPTION));
                            chat.setId(queryDocumentSnapshot.getId());
                            chat.setTipo(queryDocumentSnapshot.getString(Constants.KEY_TYPE));
                            chat.setPassword(queryDocumentSnapshot.getString(Constants.KEY_PASSWORD));
                            list.add(chat);
                        }
                    }

                    callback.onSuccess(list);
                });
    }

    @Override
    public void delete(Chat chat, OnRepositoryListCallback callback) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_FORO)
                .document(chat.getId())
                .delete()
                .addOnSuccessListener(unused -> callback.onDeleteSuccess(chat.getNombre()))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void undo(Chat chat, OnRepositoryListCallback callback) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> newChat = new HashMap<>();
        newChat.put(Constants.KEY_NAME, chat.getNombre());
        newChat.put(Constants.KEY_TYPE, chat.getTipo());
        newChat.put(Constants.KEY_PASSWORD, chat.getPassword());
        newChat.put(Constants.KEY_DESCRIPTION, chat.getDescripcion());

        database.collection(Constants.KEY_COLLECTION_FORO)
                .add(newChat)

                .addOnSuccessListener(documentReference -> {
                    callback.onUndoSuccess();
                })

                .addOnFailureListener(exception -> callback.onFailure(exception.getMessage()));

    }

    @Override
    public void updateStar(Chat chat, OnRepositoryListCallback callback) {
        if(chat.getFavorito()) {
            XRTCDatabase.databaseWriteExecutor.submit(() -> chatDAO.insert(chat));
        } else {
            XRTCDatabase.databaseWriteExecutor.submit(() -> chatDAO.delete(chat.getId()));
        }
    }

    @Override
    public void add(Chat chat, OnRepositoryManageCallback callback) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> newChat = new HashMap<>();
        newChat.put(Constants.KEY_NAME, chat.getNombre());
        newChat.put(Constants.KEY_TYPE, chat.getTipo());
        newChat.put(Constants.KEY_PASSWORD, chat.getPassword());
        newChat.put(Constants.KEY_DESCRIPTION, chat.getDescripcion());

        database.collection(Constants.KEY_COLLECTION_FORO)
                .add(newChat)

                .addOnSuccessListener(documentReference -> {
                    callback.onAddSuccess("Se ha añadido el chat " + chat.getNombre() + " con exito");
                })

                .addOnFailureListener(exception -> callback.onFailure(exception.getMessage()));

    }

    @Override
    public void edit(Chat chat, OnRepositoryManageCallback callback) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> newChat = new HashMap<>();
        newChat.put(Constants.KEY_NAME, chat.getNombre());
        newChat.put(Constants.KEY_TYPE, chat.getTipo());
        newChat.put(Constants.KEY_PASSWORD, chat.getPassword());
        newChat.put(Constants.KEY_DESCRIPTION, chat.getDescripcion());

        database.collection(Constants.KEY_COLLECTION_FORO)
                .document(chat.getId())
                .update(newChat)

                .addOnSuccessListener(documentReference -> {
                    callback.onEditSucess("Se ha editado el chat " + chat.getNombre() + " con exito");
                })

                .addOnFailureListener(exception -> callback.onFailure(exception.getMessage()));
    }

    public List<Chat> listStar() {
        List<Chat> chatStar = new ArrayList<>();
        try {
            chatStar = XRTCDatabase.databaseWriteExecutor.submit(() -> chatDAO.select()).get();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return chatStar;
    }
}
