package com.jesusc24.xroadsthroughthecastle.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "chat")
data class Chat(@NonNull var nombre: String? = null,
                @NonNull var tipo: String? = null,
                var password: String? = null,
                var descripcion: String? = null,
                @PrimaryKey(autoGenerate = true) var id: Int? = null,
                @NonNull var favorito: Boolean = false) : Serializable, Comparable<Chat> {

    var confirmPassword: String? = null

    companion object {
        const val PUBLICO = "publico"
        const val PRIVADO = "privado"
        const val TAG = "chat"
    }

    override fun compareTo(other: Chat): Int {
        return compareValuesBy(this, other, Chat::nombre, Chat::descripcion)
    }

}
