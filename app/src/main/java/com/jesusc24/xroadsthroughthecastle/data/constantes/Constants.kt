package com.jesusc24.xroadsthroughthecastle.data.constantes

import java.util.HashMap

object Constants {
    const val KEY_SEND_NOTIFICATION = "send notificaton"
    const val KEY_TITLE_NOTIFICATION = "title notification"
    const val KEY_TEXT_NOTIFICATION = "text notification"
    const val SHA_512 = "SHA-512"
    const val KEY_COLLECTION_USERS = "users"
    const val KEY_NAME = "name"
    const val KEY_EMAIL = "email"
    const val KEY_PASSWORD = "password"
    const val KEY_IMAGE = "image"
    const val KEY_PREFERENCE_NAME = "com.jesusc24.xroadsthroughthecastle_preferences"
    const val KEY_USER_ID = "userId"
    const val REMEMBER_USER = "remember"
    const val KEY_COLLECTION_CHAT = "chat"
    const val KEY_COLLECTION_FORO = "foro"
    const val KEY_SENDER_ID = "senderId"
    const val KEY_FORO_ID = "foroId"
    const val KEY_FCM_TOKEN = "fcmToken"
    const val KEY_MESSAGE = "message"
    const val KEY_TIMESTAMP = "timestamp"
    const val KEY_DESCRIPTION = "description"
    const val KEY_TYPE = "type"
    const val KEY_COLLECTION_BUGS = "bugs"
    const val KEY_DISPOSITIVO = "dispositivo"
    const val KEY_ESTADO = "estado"
    const val KEY_GRAVEDAD = "gravedad"
    const val KEY_SO = "so"
    const val KEY_ORDER_CHAT = "order chat"
    const val KEY_NOTIFICATION_CHAT = "notification chat"
    const val KEY_RIGNTONE_CHAT = "ringtone chat"
    const val KEY_ORDER_BUG = "order bug"
    const val REMOTE_MSG_AUTHORIZATION = "Authorization"
    const val REMOTE_MSG_CONTENT_TYPE = "Content-Type"
    const val REMOTE_MSG_DATA = "data"
    const val REMOTE_MSG_REGISTRATION_IDS = "registration_ids"
    const val KEY_AVAILABILITY = "availability"
    const val KEY_NAME_FORO = "name foro"
    const val KEY_BUG_ENVIADO = "enviado"
    const val KEY_BUG_APROBADO = "aprobado"
    const val KEY_BUG_ARREGLADO = "arreglado"
    const val KEY_BUG_DENAGADO = "denegado"
    const val KEY_TOTAL_TYPE = 4
    const val KEY_TOTAL_GRAVITY = 5
    const val KEY_TOTAL_DEVICE = 4
    const val KEY_TOTAL_SO = 5

    private var remoteMsgHeaders: HashMap<String, String>? = null
    @JvmStatic
    fun getRemoteMsgHeaders(): HashMap<String, String>? {
        if (remoteMsgHeaders == null) {
            remoteMsgHeaders = HashMap(0)
            remoteMsgHeaders!![REMOTE_MSG_AUTHORIZATION] =
                "key=AAAArvzQCRY:APA91bExXCRKg79PIDU1r-CN-a7mmvKzw2_YjDZZ4g9IpwrK6WqLT2m6ihL1lbyx5VUiMucFtBkVyLbgvjGF91lTAkhMsW4u8nbuz2xveGiTLmzWIHQ4LRGGNIZD-2geK4B9EXVSV0mG"
            remoteMsgHeaders!![REMOTE_MSG_CONTENT_TYPE] =
                "application/json"
        }
        return remoteMsgHeaders
    }
}