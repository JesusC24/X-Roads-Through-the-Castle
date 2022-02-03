package com.jesusc24.xroadsthroughthecastle.ui.bugs;

import com.jesusc24.xroadsthroughthecastle.data.model.Bug;

public class BugManagerPresenter implements BugManagerContract.Presenter, BugManagerContract.OnInteractorListener {

    private BugManagerContract.View view;
    private BugManagerInteractor interactor;

    public BugManagerPresenter(BugManagerContract.View view) {
        this.view = view;
        interactor = new BugManagerInteractor(this);
    }
    @Override
    public void onDestroy() {
        view = null;
        interactor = null;
    }

    @Override
    public void onAddSuccess(String message) {
        view.onAddSuccess(message);
    }

    @Override
    public void onEditSucess(String message) {
        view.onEditSucess(message);
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void add(Bug bug) {
        interactor.add(bug);
    }

    @Override
    public void edit(Bug bug) {
        interactor.edit(bug);
    }
}
