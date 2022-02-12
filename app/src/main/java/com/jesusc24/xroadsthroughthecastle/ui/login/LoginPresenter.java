package com.jesusc24.xroadsthroughthecastle.ui.login;


import android.app.Activity;
import android.content.Intent;

import com.jesusc24.xroadsthroughthecastle.data.model.User;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.OnInteractorListener {

    private LoginContract.View view;
    private LoginInteractor interactor;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        interactor = new LoginInteractor(this);
    }

    //region Métodos del contrato presenter-vista
    @Override
    public void validateCredetials(User user) {
        interactor.validateCredentials(user);
        view.showProgress();
    }

    @Override
    public void loginWithGoogle(String token, Activity activity) {
        interactor.loginWithGoogle(token, activity);
    }

    @Override
    public void loginWithFacebook() {

    }

    @Override
    public void resultGoogle(int requestCode, int resultCode, Intent data) {
        interactor.resultGoogle(requestCode, resultCode, data);
    }
    //endregion

    //region Métodos del contrato presenter-interactor
    @Override
    public void onEmailEmptyError() {
        view.hideProgress();
        view.setEmailEmptyError();
    }

    @Override
    public void onPasswordEmptyError() {
        view.hideProgress();
        view.setPasswordEmptyError();
    }

    @Override
    public void onPasswordError() {
        view.hideProgress();
        view.setPasswordError();
    }

    @Override
    public void onEmailError() {
        view.hideProgress();
        view.setEmailError();
    }

    @Override
    public void onSuccess(String message, User user) {
        view.hideProgress();
        view.onSuccess(message, user);
    }

    @Override
    public void onFailure(String message) {
        view.hideProgress();
        view.onFailure(message);
    }

    @Override
    public void onDestroy() {
        view = null;
        interactor = null;
    }
    //endregion
}
