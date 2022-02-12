package com.jesusc24.xroadsthroughthecastle.ui.base;

import java.util.List;

public interface OnRepositoryListCallback {
    void onFailure(String message);
    <T> void onSuccess(List<T> list);
    void onDeleteSuccess(String message);
    void onUndoSuccess();
}
