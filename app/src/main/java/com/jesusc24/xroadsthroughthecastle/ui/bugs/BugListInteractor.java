package com.jesusc24.xroadsthroughthecastle.ui.bugs;

import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.data.repository.BugRepository;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;

import java.util.List;

public class BugListInteractor implements OnRepositoryListCallback {
    private BugListContract.OnInteractorListener presenter;

    public BugListInteractor(BugListContract.OnInteractorListener presenter) {
        this.presenter = presenter;
    }

    public void load() {
        BugRepository.getInstance().getList(this);
    }

    public void undo(Bug bug) {
        BugRepository.getInstance().undo(bug, this);
    }

    public void delete(Bug bug) {
        BugRepository.getInstance().delete(bug, this);
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public <T> void onSuccess(List<T> list) {
        presenter.onSuccess(list);
    }

    @Override
    public void onDeleteSuccess(String message) {
        presenter.onDeleteSuccess(message);
    }

    @Override
    public void onUndoSuccess() {
        presenter.onUndoSuccess();
    }
}
