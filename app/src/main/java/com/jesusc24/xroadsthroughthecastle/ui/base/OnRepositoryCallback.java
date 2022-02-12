package com.jesusc24.xroadsthroughthecastle.ui.base;

import com.jesusc24.xroadsthroughthecastle.data.model.User;

/**
 * Innterfaz que debe implementar toda clase que esté relaiconado con la
 * respuesta del Repositorio (SECUENCIA NORMAL Y FALLO)
 * LOGIN, SINGUP
 */
public interface OnRepositoryCallback {
    void onSuccess(String message, User user);
    void onFailure(String message);
}
