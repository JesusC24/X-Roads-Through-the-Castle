package com.jesusc24.xroadsthroughthecastle.ui.bugs;

import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.ui.base.BasePresenter;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryManageCallback;

public interface BugManagerContract {
    interface View extends OnRepositoryManageCallback {

    }

    interface Presenter extends BasePresenter {
        void add(Bug bug);
        void edit(Bug bug);
    }

    interface Repository {
        //Para añadir un bug
        void add(Bug bug, OnRepositoryManageCallback callback);
        //Para editar un bug
        void edit(Bug bug, OnRepositoryManageCallback callback);
    }

    interface OnInteractorListener extends OnRepositoryManageCallback {

    }
}
