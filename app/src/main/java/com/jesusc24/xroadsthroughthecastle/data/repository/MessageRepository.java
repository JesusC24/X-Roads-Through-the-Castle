package com.jesusc24.xroadsthroughthecastle.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.model.Message;

import java.util.HashMap;
import java.util.List;

public class MessageRepository {

    private static MessageRepository instance;
    private List<Message> list;

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
        newMessage.put(Constants.KEY_TIMESTAMP,message.getFecha_envio());

        database.collection(Constants.KEY_COLLECTION_CHAT).add(newMessage);

    }
}
