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
        ChatRepositoryStatic.getInstance(this).getList();
    }

    public void undo(Chat chat) {
        ChatRepositoryStatic.getInstance(this).undo(chat);
    }

    public void delete(Chat chat) {
        ChatRepositoryStatic.getInstance(this).delete(chat);
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
