package com.jesusc24.xroadsthroughthecastle.ui.foro.message;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.data.model.Mensaje;
import com.jesusc24.xroadsthroughthecastle.data.repository.ChatRepository;
import com.jesusc24.xroadsthroughthecastle.data.repository.MessageRepository;

public class MessageInteractor {

    private MessageContract.OnInteractorListener listener;

    public MessageInteractor(MessageContract.OnInteractorListener listener) {
        this.listener = listener;
    }

    public void sendMessage(Mensaje mensaje) {
        MessageRepository.getInstance().sendMessage(mensaje);
        listener.clean();
    }

    public void activeNotification(Chat chat, String token) {
        ChatRepository.getInstance().chatEnableNotification(chat, token);
    }

    public void desableNotification(Chat chat, String token) {
        ChatRepository.getInstance().chatDesableNotification(chat, token);
    }

}
