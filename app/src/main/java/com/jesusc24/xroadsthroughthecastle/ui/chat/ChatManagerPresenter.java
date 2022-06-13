package com.jesusc24.xroadsthroughthecastle.ui.chat;


import com.jesusc24.xroadsthroughthecastle.data.model.Chat;

public class ChatManagerPresenter implements ChatManagerContract.Presenter, ChatManagerContract.OnInteractorListener {

    private ChatManagerContract.View view;
    private ChatManagerInteractor interactor;

    public ChatManagerPresenter(ChatManagerContract.View view) {
        this.view = view;
        interactor = new ChatManagerInteractor(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        interactor = null;
    }

    @Override
    public void onAddSuccess(String message) {
        view.onAddSuccess(message);
    }

    @Override
    public void onEditSucess(String message) {
        view.onEditSucess(message);
    }

    @Override
    public void onFailure(String message) {
        view.onFailure(message);
    }

    @Override
    public void add(Chat chat) {
        interactor.add(chat);
    }

    @Override
    public void edit(Chat chat) {
        interactor.edit(chat);
    }

    @Override
    public void onNameEmptyError() {
        view.setNameEmptyError();
    }

    @Override
    public void onPasswordError() {
        view.setPasswordError();
    }

    @Override
    public void onConfirmPasswordError() {
        view.setConfirmPasswordError();
    }

    @Override
    public void onPasswordDontMatch() {
        view.setPasswordDontMatch();
    }
}
