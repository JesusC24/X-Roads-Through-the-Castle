package com.jesusc24.xroadsthroughthecastle.data.repository;


import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;
import com.jesusc24.xroadsthroughthecastle.ui.foro.ChatListContract;

import java.util.ArrayList;

public class ChatRepositoryStatic implements ChatListContract.Repository {
    public static ChatRepositoryStatic instance;
    private OnRepositoryListCallback callback;
    private ArrayList<Chat> list;

    private ChatRepositoryStatic() {
        list = new ArrayList<>();
        intialice();
    }

    public static ChatRepositoryStatic getInstance(OnRepositoryListCallback callback) {
        if(instance == null) {
            instance = new ChatRepositoryStatic();
        }
        instance.callback = callback;
        return instance;
    }

    private void intialice() {
        list.add(new Chat("Grupo de clase", "Público", "primero grupo", 1));
        list.add(new Chat("Grupo 3", "Privado", "grupo al azar", 2));
        list.add(new Chat("Doctor", "Público", "medico", 3));
        list.add(new Chat("Amigo", "Privado", "mejor amigo", 4));
        list.add(new Chat("Profesor", "Público", "instituto", 5));
    }

    //region Exigencias para ser el repositorio del chat
    @Override
    public void getList() {
        callback.onSuccess(list);
    }

    @Override
    public void delete(Chat chat) {
        list.remove(chat);
        callback.onDeleteSuccess("Se ha borrado con exito el chat " + chat.getNombre());
    }

    @Override
    public void undo(Chat chat) {
        list.add(chat);
        callback.onUndoSuccess("Se ha recuperado el chat " + chat.getNombre() + " con existo");
    }
    //endregion
}
