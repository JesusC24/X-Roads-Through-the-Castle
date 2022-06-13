package com.jesusc24.xroadsthroughthecastle.ui.chat;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;

import java.util.List;

public class ChatListPresenter implements ChatListContract.Presenter, ChatListContract.OnInteractorListener {
    ChatListContract.View view;
    ChatListInteractor interactor;
    boolean order = false;
    boolean orderStar = false;
    boolean searchName = false;

    public ChatListPresenter(ChatListContract.View view) {
        this.view = view;
        interactor = new ChatListInteractor(this);
    }
    @Override
    public void onDestroy() {
        view = null;
        interactor = null;
    }

    @Override
    public void onFailure(String message) {
        view.onFailure(message);
    }

    @Override
    public <T> void onSuccess(List<T> list) {
        view.hideProgress();
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
    public void onUndoSuccess() {
        view.onUndoSuccess();
    }

    @Override
    public void load() {
        view.showProgress();
        interactor.load();
    }

    @Override
    public void delete(Chat chat) {
        interactor.delete(chat);
    }

    @Override
    public void undo(Chat chat) {
        interactor.undo(chat);
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

    @Override
    public void orderByStar() {
        view.showDataStar();
    }

    @Override
    public void search() {
        if(searchName) {
            view.hideSearch();
        } else {
            view.showSearch();
        }

        searchName =! searchName;
    }

    @Override
    public void updateStar(Chat chat) {
        interactor.updateStar(chat);
    }
}
