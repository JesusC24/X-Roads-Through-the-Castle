package com.jesusc24.xroadsthroughthecastle.data.model

import java.lang.Boolean
import kotlin.Comparator
import kotlin.Int

class ChatComparator : Comparator<Chat> {

    //Ordena por si esta en favorito o no
    override fun compare(p0: Chat, p1: Chat): Int {
        return Boolean.compare(p1.favorito, p0.favorito)
    }
}