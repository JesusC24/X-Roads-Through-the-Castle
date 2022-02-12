package com.jesusc24.xroadsthroughthecastle.ui.foro;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.data.repository.ChatRepository;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;

import java.util.List;

public class ChatListInteractor implements OnRepositoryListCallback {
    private ChatListContract.OnInteractorListener presenter;

    public ChatListInteractor(ChatListContract.OnInteractorListener presenter) {
        this.presenter = presenter;
    }

    public void load() {
        ChatRepository.getInstance().getList(this);
    }

    public void undo(Chat chat) {
        ChatRepository.getInstance().undo(chat, this);
    }

    public void delete(Chat chat) {
        ChatRepository.getInstance().delete(chat, this);
    }

    public void updateStar(Chat chat) {
        ChatRepository.getInstance().updateStar(chat, this);
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public <T> void onSuccess(List<T> list) {
        presenter.onSuccess(list);
    }

    @Override
    public void onDeleteSuccess(String message) {
        presenter.onDeleteSuccess(message);
    }

    @Override
    public void onUndoSuccess() {
        presenter.onUndoSuccess();
    }
}
