package com.jesusc24.xroadsthroughthecastle.ui.foro;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.data.repository.ChatRepositoryStatic;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;

import java.util.ArrayList;

public class ChatListInteractor implements OnRepositoryListCallback {
    private ChatListContract.OnInteractorListener presenter;

    public ChatListInteractor(ChatListContract.OnInteractorListener presenter) {
        this.presenter = presenter;
    }

    public void load() {
        ChatRepositoryStatic.getInstance().getList(this);
    }

    public void undo(Chat chat) {
        ChatRepositoryStatic.getInstance().undo(chat, this);
    }

    public void delete(Chat chat) {
        ChatRepositoryStatic.getInstance().delete(chat, this);
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public <T> void onSuccess(ArrayList<T> list) {
        presenter.onSuccess(list);
    }

    @Override
    public void onDeleteSuccess(String message) {
        presenter.onDeleteSuccess(message);
    }

    @Override
    public void onUndoSuccess(String message) {
        presenter.onUndoSuccess(message);
    }
}
