package com.jesusc24.xroadsthroughthecastle.ui.bugs;

import com.jesusc24.xroadsthroughthecastle.data.model.Bug;

import java.util.ArrayList;

public class BugListPresenter implements BugListContract.Presenter, BugListContract.OnInteractorListener {
    BugListContract.View view;
    BugListInteractor listener;
    boolean order = false;

    public BugListPresenter(BugListContract.View view) {
        this.view = view;
        listener = new BugListInteractor(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        listener = null;
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public <T> void onSuccess(ArrayList<T> list) {
        if(list.size() == 0) {
            view.showNoData();
        } else {
            view.showData(list);
        }
    }

    @Override
    public void onDeleteSuccess(String message) {
        view.onDeleteSuccess(message);
    }

    @Override
    public void onUndoSuccess(String message) {
        view.onUndoSuccess(message);
    }

    @Override
    public void load() {
        listener.load();
    }

    @Override
    public void delete(Bug bug) {
        listener.delete(bug);
    }

    @Override
    public void undo(Bug bug) {
        listener.undo(bug);
    }

    @Override
    public void order() {
        if(order) {
            order = false;
            view.showDataInverseOrder();
        } else {
            order = true;
            view.showDataOrder();
        }
    }
}
