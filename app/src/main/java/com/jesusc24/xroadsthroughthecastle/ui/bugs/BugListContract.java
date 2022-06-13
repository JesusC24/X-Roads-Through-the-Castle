package com.jesusc24.xroadsthroughthecastle.ui.bugs;

import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.ui.base.BasePresenter;
import com.jesusc24.xroadsthroughthecastle.ui.base.IProgressView;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;

import java.util.List;


public interface BugListContract {
    interface View extends OnRepositoryListCallback, IProgressView {
        <T> void showData(List<T> list);
        void showNoData();
        //Ordena de la A-Z
        void showDataOrder();
        //Ordena de la Z-A
        void showDataInverseOrder();
        void showByEstadoOrder();
        void hideSearch();
        void showSearch();
    }

    /**
     * Interfaz que debe implementar el presenter
     */
    interface Presenter extends BasePresenter {
        //Cargar los daots
        void load();
        //Cuando se realiza una pulsación larga se elimina
        void delete(Bug bug);
        //Cuando el usuario pulsa la opción undo del SnackBar
        void undo(Bug bug);
        //Que la lista se ordene por nombre
        void order();
        void orderByEstado();
        void search();
    }

    /**
     * Interfaz que debe implementar toda clase que quiera ser un Repositorio
     * Lists
     */
    interface Repository {
        //Cargar los daots
        void getList(OnRepositoryListCallback callback);
        //Cuando se realiza una pulsación larga se elimina
        void delete(Bug bug, OnRepositoryListCallback callback);
        //Cuando el usuario pulsa la opción undo del SnackBar
        void undo(Bug bug, OnRepositoryListCallback callback);
    }

    /**
     * * Interfaz que debe implementar el Listener de Interactor.
     * Esta interfaz muestra las posibles alternativas de los casos de uso
     * - LISTAR ELEMENTOS (getList)
     * - ELIMINAR (delete)
     * - DESHACER (Undo)
     */
    interface OnInteractorListener extends OnRepositoryListCallback {
    }


}
