package com.jesusc24.xroadsthroughthecastle.ui.foro;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.ui.base.BasePresenter;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryManageCallback;


public interface ChatManagerContract {

    interface View extends OnRepositoryManageCallback {
        void setNameEmptyError();
        void setPasswordError();
        void setConfirmPasswordError();
        void setPasswordDontMatch();
    }


    interface Presenter extends BasePresenter {
        void add(Chat chat);
        void edit(Chat chat);
    }

    interface Repository {
        //Para añadir un chat
        void add(Chat chat, OnRepositoryManageCallback callback);
        //Para editar un chat
        void edit(Chat chat, OnRepositoryManageCallback callback);
    }

    interface OnInteractorListener extends OnRepositoryManageCallback {
        void onNameEmptyError();
        void onPasswordError();
        void onConfirmPasswordError();
        void onPasswordDontMatch();
    }
}
