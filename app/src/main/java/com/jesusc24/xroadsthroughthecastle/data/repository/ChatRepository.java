package com.jesusc24.xroadsthroughthecastle.data.repository;

import com.jesusc24.xroadsthroughthecastle.data.XRTCDatabase;
import com.jesusc24.xroadsthroughthecastle.data.dao.ChatDAO;
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryManageCallback;
import com.jesusc24.xroadsthroughthecastle.ui.foro.ChatListContract;
import com.jesusc24.xroadsthroughthecastle.ui.foro.ChatManagerContract;

import java.util.ArrayList;
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
        try {
            list = XRTCDatabase.databaseWriteExecutor.submit(() -> chatDAO.select()).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        callback.onSuccess(list);
    }

    @Override
    public void delete(Chat chat, OnRepositoryListCallback callback) {
        XRTCDatabase.databaseWriteExecutor.submit(() -> chatDAO.delete(chat));
        callback.onDeleteSuccess(chat.getNombre());
    }

    @Override
    public void undo(Chat chat, OnRepositoryListCallback callback) {
        XRTCDatabase.databaseWriteExecutor.submit(() -> chatDAO.insert(chat));
        callback.onUndoSuccess();
    }

    @Override
    public void updateStar(Chat chat, OnRepositoryListCallback callback) {
        XRTCDatabase.databaseWriteExecutor.submit(() -> chatDAO.update(chat));
    }

    @Override
    public void add(Chat chat, OnRepositoryManageCallback callback) {
        XRTCDatabase.databaseWriteExecutor.submit(() -> chatDAO.insert(chat));
        callback.onAddSuccess("Se ha añadido el chat " + chat.getNombre() + " con exito");
    }

    @Override
    public void edit(Chat chat, OnRepositoryManageCallback callback) {
        XRTCDatabase.databaseWriteExecutor.submit(() -> chatDAO.update(chat));
        callback.onEditSucess(chat.getNombre());
    }
}
