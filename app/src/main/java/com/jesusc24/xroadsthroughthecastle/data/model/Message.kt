package com.jesusc24.xroadsthroughthecastle.data.model

import java.sql.Timestamp

data class Message(var texto: String,
                   var usuario: String,
                   var fecha_envio: Timestamp)
