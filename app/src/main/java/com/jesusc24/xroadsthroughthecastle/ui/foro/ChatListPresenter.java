package com.jesusc24.xroadsthroughthecastle.ui.foro;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;

import java.util.ArrayList;

public class ChatListPresenter implements ChatListContract.Presenter, ChatListContract.OnInteractorListener {
    ChatListContract.View view;
    ChatListInteractor listener;
    boolean order = false;

    public ChatListPresenter(ChatListContract.View view) {
        this.view = view;
        listener = new ChatListInteractor(this);
    }
    @Override
    public void onDestroy() {
        view = null;
        listener = null;
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public <T> void onSuccess(ArrayList<T> list) {
        if(list.size() == 0) {
            view.showNoData();
        } else {
            view.showData(list);
        }
    }

    @Override
    public void onDeleteSuccess(String message) {
        view.onDeleteSuccess(message);
    }

    @Override
    public void onUndoSuccess(String message) {
        view.onUndoSuccess(message);
    }

    @Override
    public void load() {
        listener.load();
    }

    @Override
    public void delete(Chat chat) {
        listener.delete(chat);
    }

    @Override
    public void undo(Chat chat) {
        listener.undo(chat);
    }

    @Override
    public void order() {
        if(order) {
            order = false;
            view.showDataInverseOrder();
        } else {
            order = true;
            view.showDataOrder();
        }
    }
}
