package com.jesusc24.xroadsthroughthecastle.data.constantes;

import java.util.HashMap;

public class Constants {
    public static final String SHA_512 = "SHA-512";
    public static final String KEY_COLLECTION_USERS = "users";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_PREFERENCE_NAME = "com.jesusc24.xroadsthroughthecastle_preferences";
    public static final String KEY_USER_ID = "userId";
    public static final String REMEMBER_USER = "remember";
    public static final String KEY_COLLECTION_CHAT = "chat";
    public static final String KEY_COLLECTION_FORO = "foro";
    public static final String KEY_SENDER_ID = "senderId";
    public static final String KEY_FORO_ID = "foroId";
    public static final String KEY_FCM_TOKEN = "fcmToken";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_TYPE = "type";
    public static final String KEY_COLLECTION_BUGS = "bugs";
    public static final String KEY_DISPOSITIVO = "dispositivo";
    public static final String KEY_ESTADO = "estado";
    public static final String KEY_GRAVEDAD = "gravedad";
    public static final String KEY_SO = "so";
    public static final String KEY_ORDER_CHAT = "order chat";
    public static final String KEY_NOTIFICATION_CHAT = "notification chat";
    public static final String KEY_RIGNTONE_CHAT = "ringtone chat";
    public static final String KEY_ORDER_BUG = "order bug";
    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";
    public static final String KEY_AVAILABILITY = "availability";
    public static final String KEY_NAME_FORO = "name foro";



    public static HashMap<String, String> remoteMsgHeaders = null;

    public static HashMap<String, String> getRemoteMsgHeaders() {
        if(remoteMsgHeaders == null) {
            remoteMsgHeaders = new HashMap<>(0);
            remoteMsgHeaders.put(
                    REMOTE_MSG_AUTHORIZATION,
                    "key=AAAArvzQCRY:APA91bExXCRKg79PIDU1r-CN-a7mmvKzw2_YjDZZ4g9IpwrK6WqLT2m6ihL1lbyx5VUiMucFtBkVyLbgvjGF91lTAkhMsW4u8nbuz2xveGiTLmzWIHQ4LRGGNIZD-2geK4B9EXVSV0mG"
            );

            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application/json"
            );

        }

        return remoteMsgHeaders;
    }
}
