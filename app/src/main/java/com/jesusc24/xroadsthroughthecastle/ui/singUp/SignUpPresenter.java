package com.jesusc24.xroadsthroughthecastle.ui.singUp;


import com.jesusc24.xroadsthroughthecastle.data.model.User;

public class SignUpPresenter implements SignUpContract.Presenter, SignUpContract.OnInteractorListener {

    SignUpContract.View view;
    SignUpInteractor interactor;

    public SignUpPresenter(SignUpContract.View view) {
        this.view = view;
        interactor = new SignUpInteractor(this);
    }

    @Override
    public void validateSingUp(User user) {
        interactor.validarSingUp(user);
    }

    @Override
    public void onUserEmptyError() {
        view.setUserEmptyError();
    }

    @Override
    public void onEmailEmptyError() {
        view.setEmailEmptyError();
    }

    @Override
    public void onPasswordEmptyError() {
        view.setPasswordError();
    }

    @Override
    public void onConfirmPasswordEmptyError() {
        view.setConfirmPasswordEmptyError();
    }

    @Override
    public void onPasswordError() {
        view.setPasswordError();
    }

    @Override
    public void onEmailError() {
        view.setEmailError();
    }

    @Override
    public void onPasswordDontMatch() {
        view.setPasswordDontMatch();
    }

    @Override
    public void onSuccess(String message) {
        view.onSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        view.onFailure(message);
    }

    @Override
    public void onDestroy() {
        view = null;
        interactor = null;
    }
}
