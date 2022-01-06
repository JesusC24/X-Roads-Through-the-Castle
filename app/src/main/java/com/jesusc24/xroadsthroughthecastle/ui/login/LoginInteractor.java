package com.jesusc24.xroadsthroughthecastle.ui.login;

import android.os.Handler;
import android.text.TextUtils;

import com.jesusc24.xroadsthroughthecastle.data.model.User;
import com.jesusc24.xroadsthroughthecastle.data.repository.UserRepositoryImpl;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryCallback;
import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils;


public class LoginInteractor implements OnRepositoryCallback {

    private LoginContract.OnInteractorListener listener;

    public LoginInteractor(LoginContract.OnInteractorListener presenter) {
        this.listener = presenter;
    }

    public void validateCredentials(User user) {
        //En base a lo que suceda avisará a su Listener/Presentador
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Gestionar las alternativas del caso de uso
                if(TextUtils.isEmpty(user.getEmail())) {
                    listener.onEmailEmptyError();
                    return;
                }

                if(TextUtils.isEmpty(user.getPassword())) {
                    listener.onPasswordEmptyError();
                    return;
                }

                if(!CommonUtils.isPasswordValid(user.getPassword())) {
                    listener.onPasswordError();
                    return;
                }

                if(!CommonUtils.isEmailValid(user.getEmail())) {
                    listener.onEmailError();
                    return;
                }

                UserRepositoryImpl.getInstance(LoginInteractor.this).login(user);
            }
        }, 2000);
    }


    //Estos métodos vienen de la respuesta que nos de el Repositorio
    @Override
    public void onSuccess(String message) {
        listener.onSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        listener.onFailure(message);
    }
}
