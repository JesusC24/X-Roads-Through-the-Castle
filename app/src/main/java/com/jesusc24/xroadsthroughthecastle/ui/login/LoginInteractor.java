package com.jesusc24.xroadsthroughthecastle.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.jesusc24.xroadsthroughthecastle.data.model.User;
import com.jesusc24.xroadsthroughthecastle.data.repository.UserRepository;
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

                UserRepository.getInstance(LoginInteractor.this).login(user);
            }
        }, 2000);
    }

    public void loginWithGoogle(String token, Activity activity) {
        UserRepository.getInstance(LoginInteractor.this).firebaseAuthWithGoogle(token, activity);
    }

    public void loginWithFacebook() {

    }

    public void resultGoogle(int requestCode, int resultCode, Intent data) {
        UserRepository.getInstance(this).resultGoogle(requestCode, resultCode, data);
    }


    //Estos métodos vienen de la respuesta que nos de el Repositorio
    @Override
    public void onSuccess(String message, User user) {
        listener.onSuccess(message, user);
    }

    @Override
    public void onFailure(String message) {
        listener.onFailure(message);
    }
}
