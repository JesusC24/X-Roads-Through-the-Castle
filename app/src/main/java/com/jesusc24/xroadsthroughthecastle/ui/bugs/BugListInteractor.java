package com.jesusc24.xroadsthroughthecastle.ui.bugs;

import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.data.repository.BugRepositoryStatic;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;

import java.util.ArrayList;

public class BugListInteractor implements OnRepositoryListCallback {
    private BugListContract.OnInteractorListener presenter;

    public BugListInteractor(BugListContract.OnInteractorListener presenter) {
        this.presenter = presenter;
    }

    public void load() {
        BugRepositoryStatic.getInstance().getList(this);
    }

    public void undo(Bug bug) {
        BugRepositoryStatic.getInstance().undo(bug, this);
    }

    public void delete(Bug bug) {
        BugRepositoryStatic.getInstance().delete(bug, this);
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public <T> void onSuccess(ArrayList<T> list) {
        presenter.onSuccess(list);
    }

    @Override
    public void onDeleteSuccess(String message) {
        presenter.onDeleteSuccess(message);
    }

    @Override
    public void onUndoSuccess(String message) {
        presenter.onUndoSuccess(message);
    }
}
