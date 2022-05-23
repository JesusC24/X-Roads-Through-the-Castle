package com.jesusc24.xroadsthroughthecastle.ui.foro.message;

import com.jesusc24.xroadsthroughthecastle.data.model.Message;

import java.util.List;

public class MessagePresenter implements MessageContract.Presenter, MessageContract.OnInteractorListener {

    MessageContract.View view;
    MessageInteractor interactor;

    public MessagePresenter(MessageContract.View view) {
        this.view = view;
        interactor = new MessageInteractor(this);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void sendMessage(Message message) {
        interactor.sendMessage(message);
    }

    @Override
    public void clean() {
        view.clean();
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public <T> void onSuccess(List<T> list) {

    }

    @Override
    public void onDeleteSuccess(String message) {

    }

    @Override
    public void onUndoSuccess() {

    }
}
