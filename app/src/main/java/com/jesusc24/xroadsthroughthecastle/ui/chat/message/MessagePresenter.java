package com.jesusc24.xroadsthroughthecastle.ui.chat.message;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.data.model.Message;

public class MessagePresenter implements MessageContract.Presenter, MessageContract.OnInteractorListener {

    MessageContract.View view;
    MessageInteractor interactor;

    public MessagePresenter(MessageContract.View view) {
        this.view = view;
        interactor = new MessageInteractor(this);
    }

    @Override
    public void sendMessage(Message message) {
        interactor.sendMessage(message);
        view.cargarNotification(message);
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
