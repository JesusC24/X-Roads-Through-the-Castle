package com.jesusc24.xroadsthroughthecastle.data.repository;


import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryManageCallback;
import com.jesusc24.xroadsthroughthecastle.ui.foro.ChatListContract;
import com.jesusc24.xroadsthroughthecastle.ui.foro.ChatManagerContract;

import java.util.ArrayList;

public class ChatRepositoryStatic implements ChatListContract.Repository, ChatManagerContract.Repository {
    public static ChatRepositoryStatic instance;
    private ArrayList<Chat> list;

    private ChatRepositoryStatic() {
        list = new ArrayList<>();
        intialice();
    }

    public static ChatRepositoryStatic getInstance() {
        if(instance == null) {
            instance = new ChatRepositoryStatic();
        }

        return instance;
    }

    private void intialice() {
        list.add(new Chat("Grupo de clase", Chat.PRIVADO, "primero grupo", 1));
        list.add(new Chat("Grupo 3", Chat.PUBLICO, "grupo al azar", 2));
        list.add(new Chat("Doctor", Chat.PUBLICO, "medico", 3));
        list.add(new Chat("Amigo", Chat.PRIVADO, "mejor amigo", 4));
        list.add(new Chat("Profesor", Chat.PUBLICO, "instituto", 5));
    }

    @Override
    public void getList(OnRepositoryListCallback callback) {
        callback.onSuccess(list);
    }

    @Override
    public void delete(Chat chat, OnRepositoryListCallback callback) {
        list.remove(chat);
        callback.onDeleteSuccess("Se ha borrado con exito el chat " + chat.getNombre());
    }

    @Override
    public void undo(Chat chat, OnRepositoryListCallback callback) {
        list.add(chat);
        callback.onUndoSuccess("Se ha recuperado el chat " + chat.getNombre() + " con existo");
    }

    @Override
    public void add(Chat chat, OnRepositoryManageCallback callback) {
        list.add(chat);
        callback.onAddSuccess("Se ha añadido el chat " + chat.getNombre() + "con exito");
    }

    @Override
    public void edit(Chat chat, OnRepositoryManageCallback callback) {
        int posicionEliminar = 0;
        for(Chat c : list) {
            if(c.getNombre().equals(chat.getNombre())) {
                posicionEliminar = list.indexOf(c);
            }
        }

        list.remove(posicionEliminar);
        list.add(chat);

        callback.onEditSucess("Se ha editado el chat" + chat.getNombre() + " con exito");
    }
}
