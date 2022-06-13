package com.jesusc24.xroadsthroughthecastle.data.model

import java.util.*

data class Mensaje(var texto: String,
                   var envioId: String,
                   var chatId: String,
                   var fecha_envio: Date,
                   var imagen: String?,
                   var user: String
)




