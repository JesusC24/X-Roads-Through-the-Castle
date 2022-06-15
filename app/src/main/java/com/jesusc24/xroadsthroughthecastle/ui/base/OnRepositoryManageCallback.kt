package com.jesusc24.xroadsthroughthecastle.ui.base

interface OnRepositoryManageCallback {
    fun onAddSuccess(message: String?)
    fun onEditSucess(message: String?)
    fun onFailure(message: String?)
}