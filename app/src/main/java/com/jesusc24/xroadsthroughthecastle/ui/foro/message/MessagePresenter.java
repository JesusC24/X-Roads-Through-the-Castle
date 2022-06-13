package com.jesusc24.xroadsthroughthecastle.ui.foro.message;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.data.model.Mensaje;

public class MessagePresenter implements MessageContract.Presenter, MessageContract.OnInteractorListener {

    MessageContract.View view;
    MessageInteractor interactor;

    public MessagePresenter(MessageContract.View view) {
        this.view = view;
        interactor = new MessageInteractor(this);
    }

    @Override
    public void sendMessage(Mensaje mensaje) {
        interactor.sendMessage(mensaje);
        view.cargarNotification(mensaje);
    }

    @Override
    public void activeNotification(Chat chat, String token) {
        interactor.activeNotification(chat, token);
    }

    @Override
    public void desableNotification(Chat chat, String token) {
        interactor.desableNotification(chat, token);
    }



    @Override
    public void clean() {
        view.clean();
    }

}
