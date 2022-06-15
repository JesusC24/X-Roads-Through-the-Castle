package com.jesusc24.xroadsthroughthecastle.ui.base

interface BasePresenter {
    //En este método se pone a null el objeto view y el interactor dentro del presenter
    //Si no se hace tenemos Memory Leaks al reiniciar la Activity de la vista
    fun onDestroy()
}