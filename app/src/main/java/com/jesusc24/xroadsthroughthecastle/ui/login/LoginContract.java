package com.jesusc24.xroadsthroughthecastle.ui.login;


import com.jesusc24.xroadsthroughthecastle.data.model.User;
import com.jesusc24.xroadsthroughthecastle.ui.base.BasePresenter;
import com.jesusc24.xroadsthroughthecastle.ui.base.IProgressView;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryCallback;

/**
 * Esta interfaz es el contrato de las clases de LOGIN
 */
public interface LoginContract {
    /**
     * La interfaz que debe implementar mi vista
     */
    interface View extends OnRepositoryCallback, IProgressView {
        //Alternativas del caso de uso.
        //Set porque se modifica elementos de la vista
        void setEmailEmptyError();
        void setPasswordEmptyError();
        void setPasswordError();
        void setEmailError();
    }

    /**
     * La interfaz que debe implementar el Presenter
     */
    interface Presenter extends BasePresenter {
        void validateCredetials(User user);
    }

    /**
     * Interfaz que debe implementar toda clase que quiera ser un Repository para login
     * Esta INTERFAZ es las posibles ALTERNATIVAS del caso de uso de LOGIN
     */
    interface Repository {
        void login(User user);
    }

    /**
     * Interfaz que debe implementar el Listener del Interactor
     */
    interface OnInteractorListener extends OnRepositoryCallback {
        void onEmailEmptyError();
        void onPasswordEmptyError();
        void onPasswordError();
        void onEmailError();
    }
}
