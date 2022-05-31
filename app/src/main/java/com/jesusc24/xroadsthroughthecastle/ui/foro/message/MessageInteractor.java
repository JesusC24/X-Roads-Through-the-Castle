package com.jesusc24.xroadsthroughthecastle.ui.foro.message;

import com.jesusc24.xroadsthroughthecastle.data.model.Message;
import com.jesusc24.xroadsthroughthecastle.data.repository.MessageRepository;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryCallback;

public class MessageInteractor implements OnRepositoryCallback {

    private MessageContract.OnInteractorListener listener;

    public MessageInteractor(MessageContract.OnInteractorListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(int message) {

    }

    public void sendMessage(Message message) {
        MessageRepository.getInstance().sendMessage(message);
        listener.clean();
    }


}
