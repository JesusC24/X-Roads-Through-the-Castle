package com.jesusc24.xroadsthroughthecastle.ui.bugs;

import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.data.repository.BugRepositoryStatic;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryManageCallback;

public class BugManagerInteractor implements OnRepositoryManageCallback {

    private BugManagerContract.OnInteractorListener listener;
    public BugManagerInteractor(BugManagerContract.OnInteractorListener listener) {
        this.listener = listener;
    }

    public void add(Bug bug) {
        BugRepositoryStatic.getInstance().add(bug, this);
    }

    public void edit(Bug bug) {
        BugRepositoryStatic.getInstance().edit(bug, this);
    }

    @Override
    public void onAddSuccess(String message) {
        listener.onAddSuccess(message);
    }

    @Override
    public void onEditSucess(String message) {
        listener.onEditSucess(message);
    }

    @Override
    public void onFailure(String message) {

    }
}
