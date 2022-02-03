package com.jesusc24.xroadsthroughthecastle.ui.foro;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.data.repository.ChatRepositoryStatic;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryManageCallback;
import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils;

public class ChatManagerInteractor implements OnRepositoryManageCallback {

    private ChatManagerContract.OnInteractorListener listener;
    public ChatManagerInteractor(ChatManagerContract.OnInteractorListener listener) {
        this.listener = listener;
    }

    public boolean validarDatos(Chat chat) {

        if(chat.getNombre().isEmpty()) {
            listener.onNameEmptyError();
            return false;
        }

        if(chat.getTipo().equals(Chat.PRIVADO)) {
            if(chat.getPassword().isEmpty() || !(CommonUtils.isPasswordValid(chat.getPassword()))) {
                listener.onPasswordError();
                return false;
            }

            if(chat.getConfirmPassword().isEmpty() ||!(CommonUtils.isPasswordValid(chat.getConfirmPassword()))) {
                listener.onConfirmPasswordError();
                return false;
            }

            if(!(chat.getPassword().contentEquals(chat.getConfirmPassword()))) {
                listener.onPasswordDontMatch();
                return false;
            }
        }

        return true;
    }

    public void add(Chat chat) {
        if(validarDatos(chat)) {
            ChatRepositoryStatic.getInstance().add(chat, this);
        }
    }

    public void edit(Chat chat) {
        if(validarDatos(chat)) {
            ChatRepositoryStatic.getInstance().edit(chat, this);
        }
    }


    @Override
    public void onAddSuccess(String message) {
        listener.onAddSuccess(message);
    }

    @Override
    public void onEditSucess(String message) {
        listener.onEditSucess(message);
    }

    @Override
    public void onFailure(String message) {

    }
}
