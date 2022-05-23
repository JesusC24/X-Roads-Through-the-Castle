package com.jesusc24.xroadsthroughthecastle.ui.foro.message;

import com.jesusc24.xroadsthroughthecastle.data.model.Message;
import com.jesusc24.xroadsthroughthecastle.ui.base.BasePresenter;
import com.jesusc24.xroadsthroughthecastle.ui.base.IProgressView;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;

import java.util.List;

public interface MessageContract {
    /**
     * Esta interfaz tiene los siguientes métodos,
     * - La respuesta del respositorio
     * - Los métodos necesarios para mostrar un progeso
     * - Los métodos necesario para gestionar los datos de un RecycleView
     */
    interface View extends OnRepositoryListCallback, IProgressView {
        <T> void showData(List<T> list);
        void showNoData();
        void clean();
    }

    /**
     * Interfaz que debe implementar el presenter
     */
    interface Presenter extends BasePresenter {
        //Cargar los datos
        void sendMessage(Message message);
    }

    /**
     * Interfaz que debe implementar toda clase que quiera ser un Repositorio
     * Lists
     */
    interface Repository {
        //Cargar los datos
        void getList(OnRepositoryListCallback callback);
    }

    /**
     * Interfaz que debe implementar el Listener de Interactor.
     * Esta interfaz muestra las posibles alternativas de los casos de uso
     * - LISTAR ELEMENTOS (getList)
     * - ELIMINAR (delete)
     * - DESHACER (Undo)
     */
    interface OnInteractorListener extends OnRepositoryListCallback {
        void clean();
    }
}
