package com.jesusc24.xroadsthroughthecastle.ui.login;


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
    public void onSuccess(String message) {
        view.hideProgress();
        view.onSuccess(message);
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
