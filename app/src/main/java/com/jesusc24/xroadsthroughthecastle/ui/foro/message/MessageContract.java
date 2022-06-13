package com.jesusc24.xroadsthroughthecastle.ui.foro.message;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.data.model.Mensaje;

public interface MessageContract {
    /**
     * Esta interfaz tiene los siguientes métodos,
     * - La respuesta del respositorio
     * - Los métodos necesarios para mostrar un progeso
     * - Los métodos necesario para gestionar los datos de un RecycleView
     */
    interface View {
        void clean();
        void cargarNotification(Mensaje mensaje);
    }

    /**
     * Interfaz que debe implementar el presenter
     */
    interface Presenter  {
        //Cargar los datos
        void sendMessage(Mensaje mensaje);
        void activeNotification(Chat chat, String token);
        void desableNotification(Chat chat, String token);
    }

    /**
     * Interfaz que debe implementar toda clase que quiera ser un Repositorio
     * Lists
     */
    interface Repository {
        //Cargar los datos
        void sendMessage(Mensaje mensaje);
    }

    /**
     * Interfaz que debe implementar el Listener de Interactor.
     * Esta interfaz muestra las posibles alternativas de los casos de uso
     * - LISTAR ELEMENTOS (getList)
     * - ELIMINAR (delete)
     * - DESHACER (Undo)
     */
    interface OnInteractorListener {
        void clean();
    }
}
