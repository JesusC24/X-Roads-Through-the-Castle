package com.jesusc24.xroadsthroughthecastle.ui.foro;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.ui.base.BasePresenter;
import com.jesusc24.xroadsthroughthecastle.ui.base.IProgressView;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;



public interface ChatManagerContract {

    interface View extends OnRepositoryListCallback, IProgressView {
        void setElementEmpty(String message);
        void setErrorName();
        void setCaracteresEspeciales(String message);
        void setErrorPassword();
    }


    interface Presenter extends BasePresenter {
        void validarDatos(Chat chat);
    }


    interface OnInteractorListener extends OnRepositoryListCallback {
        void valoresNulo(String message);
        void nombreCorto();
        void errorCaracteresEspeciales(String message);
        void errorPassword(String message);
    }
}
