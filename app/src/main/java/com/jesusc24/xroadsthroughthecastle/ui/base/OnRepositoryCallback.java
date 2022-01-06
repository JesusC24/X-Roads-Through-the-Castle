package com.jesusc24.xroadsthroughthecastle.ui.base;

/**
 * Innterfaz que debe implementar toda clase que esté relaiconado con la
 * respuesta del Repositorio (SECUENCIA NORMAL Y FALLO)
 * LOGIN, SINGUP
 */
public interface OnRepositoryCallback {
    void onSuccess(String message);
    void onFailure(String message);
}
