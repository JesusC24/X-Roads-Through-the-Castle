package com.jesusc24.xroadsthroughthecastle.ui.foro;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.ui.base.BasePresenter;
import com.jesusc24.xroadsthroughthecastle.ui.base.IProgressView;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;

import java.util.ArrayList;

public interface ChatListContract {
    /**
     * Esta interfaz tiene los siguientes métodos,
     * - La respuesta del respositorio
     * - Los métodos necesarios para mostrar un progeso
     * - Los métodos necesario para gestionar los datos de un RecycleView
     */
    interface View extends OnRepositoryListCallback, IProgressView {
        <T> void showData(ArrayList<T> list);
        void showNoData();
        //Ordena de la A-Z
        void showDataOrder();
        //Ordena de la Z-A
        void showDataInverseOrder();
    }

    /**
     * Interfaz que debe implementar el presenter
     */
    interface Presenter extends BasePresenter {
        //Cargar los daots
        void load();
        //Cuando se realiza una pulsación larga se elimina
        void delete(Chat chat);
        //Cuando el usuario pulsa la opción undo del SnackBar
        void undo(Chat chat);
        //Que la lista se ordene por nombre
        void order();
    }

    /**
     * Interfaz que debe implementar toda clase que quiera ser un Repositorio
     * Lists
     */
    interface Repository {
        //Cargar los daots
        void getList(OnRepositoryListCallback callback);
        //Cuando se realiza una pulsación larga se elimina
        void delete(Chat chat, OnRepositoryListCallback callback);
        //Cuando el usuario pulsa la opción undo del SnackBar
        void undo(Chat chat, OnRepositoryListCallback callback);
    }

    /**
     * Interfaz que debe implementar el Listener de Interactor.
     * Esta interfaz muestra las posibles alternativas de los casos de uso
     * - LISTAR ELEMENTOS (getList)
     * - ELIMINAR (delete)
     * - DESHACER (Undo)
     */
    interface OnInteractorListener extends OnRepositoryListCallback {
    }
}
