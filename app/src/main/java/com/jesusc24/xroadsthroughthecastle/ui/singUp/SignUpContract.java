package com.jesusc24.xroadsthroughthecastle.ui.singUp;


import com.jesusc24.xroadsthroughthecastle.data.model.User;
import com.jesusc24.xroadsthroughthecastle.ui.base.BasePresenter;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryCallback;
import com.jesusc24.xroadsthroughthecastle.ui.login.LoginContract;

public interface SignUpContract {
    interface View extends OnRepositoryCallback {

        //Nombre de usuario vacio
        void setUserEmptyError();

        //Confirmación de Password vacío
        void setConfirmPasswordEmptyError();

        //No coinciden las contraseñas
        void setPasswordDontMatch();

        void setEmailEmptyError();
        void setPasswordEmptyError();
        void setPasswordError();
        void setEmailError();
    }

    interface Presenter extends BasePresenter {
        void validateSingUp(User user);
    }


    /*
    Interfaz que debe implementar mi vista
     */
    interface OnInteractorListener extends LoginContract.OnInteractorListener, OnRepositoryCallback {
        //Nombre de usuario vacío
        void onUserEmptyError();

        //Confirmación de Passwrod vacío
        void onConfirmPasswordEmptyError();

        //No coinciden las contraseñas
        void onPasswordDontMatch();
    }



    interface Repository {
        void signUp(User user);
    }
}
