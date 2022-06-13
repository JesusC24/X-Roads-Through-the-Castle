package com.jesusc24.xroadsthroughthecastle.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.model.Mensaje;
import com.jesusc24.xroadsthroughthecastle.ui.foro.message.MessageContract;

import java.util.HashMap;

public class MessageRepository implements MessageContract.Repository {

    private static MessageRepository instance;

    public static MessageRepository getInstance() {
        if(instance == null) {
            instance = new MessageRepository();
        }

        return instance;
    }

    public void sendMessage(Mensaje mensaje) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> newMessage = new HashMap<>();

        newMessage.put(Constants.KEY_SENDER_ID, mensaje.getEnvioId());
        newMessage.put(Constants.KEY_FORO_ID, mensaje.getChatId());
        newMessage.put(Constants.KEY_MESSAGE, mensaje.getTexto());
        newMessage.put(Constants.KEY_TIMESTAMP, mensaje.getFecha_envio());
        newMessage.put(Constants.KEY_NAME, mensaje.getUser());
        newMessage.put(Constants.KEY_IMAGE, mensaje.getImagen());

        database.collection(Constants.KEY_COLLECTION_CHAT).add(newMessage);
    }
}
