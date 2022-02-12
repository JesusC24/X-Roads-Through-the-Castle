package com.jesusc24.xroadsthroughthecastle.ui.singUp;

import android.text.TextUtils;

import com.jesusc24.xroadsthroughthecastle.data.model.User;
import com.jesusc24.xroadsthroughthecastle.data.repository.UserRepository;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryCallback;
import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils;

public class SignUpInteractor implements OnRepositoryCallback {

    private SignUpContract.OnInteractorListener presenter;
    private SignUpContract.Repository repository;

    public SignUpInteractor(SignUpContract.OnInteractorListener presenter) {
        this.presenter = presenter;
    }
    public void validarSingUp(User user) {
        //Gestionar los diferentes casos de uso
        if(TextUtils.isEmpty(user.getUser())) {
            presenter.onUserEmptyError();
            return;
        }

        if(TextUtils.isEmpty(user.getEmail())) {
            presenter.onEmailEmptyError();
            return;
        }

        if(TextUtils.isEmpty(user.getPassword())) {
            presenter.onPasswordEmptyError();
            return;
        }

        if(TextUtils.isEmpty(user.getConfirmPassword())) {
            presenter.onConfirmPasswordEmptyError();
            return;
        }

        if(!(CommonUtils.isPasswordValid(user.getPassword()))) {
            presenter.onPasswordError();
            return;
        }

        if(!(CommonUtils.isEmailValid(user.getEmail()))) {
            presenter.onEmailError();
            return;
        }

        if(!(user.getPassword().contentEquals(user.getConfirmPassword()))) {
            presenter.onPasswordDontMatch();
            return;
        }

        UserRepository.getInstance(SignUpInteractor.this).signUp(user);
    }


    @Override
    public void onSuccess(String message, User user) {
        presenter.onSuccess(message, user);
    }

    @Override
    public void onFailure(String message) {
        presenter.onFailure(message);
    }

}
