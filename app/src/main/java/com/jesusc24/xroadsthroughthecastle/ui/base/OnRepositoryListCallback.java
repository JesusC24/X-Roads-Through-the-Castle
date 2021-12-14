package com.jesusc24.xroadsthroughthecastle.ui.base;

import java.util.ArrayList;

public interface OnRepositoryListCallback {
    void onFailure(String message);
    <T> void onSuccess(ArrayList<T> list);
    void onDeleteSuccess(String message);
    void onUndoSuccess(String message);
}
