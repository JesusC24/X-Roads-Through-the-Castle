package com.jesusc24.xroadsthroughthecastle.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.model.Message;
import com.jesusc24.xroadsthroughthecastle.ui.chat.message.MessageContract;

import java.util.HashMap;

public class MessageRepository implements MessageContract.Repository {

    private static MessageRepository instance;

    public static MessageRepository getInstance() {
        if(instance == null) {
            instance = new MessageRepository();
        }

        return instance;
    }

    public void sendMessage(Message message) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> newMessage = new HashMap<>();

        newMessage.put(Constants.KEY_SENDER_ID, message.getEnvioId());
        newMessage.put(Constants.KEY_FORO_ID, message.getChatId());
        newMessage.put(Constants.KEY_MESSAGE, message.getTexto());
        newMessage.put(Constants.KEY_TIMESTAMP, message.getFecha_envio());
        newMessage.put(Constants.KEY_NAME, message.getUser());
        newMessage.put(Constants.KEY_IMAGE, message.getImagen());

        database.collection(Constants.KEY_COLLECTION_CHAT).add(newMessage);
    }
}
