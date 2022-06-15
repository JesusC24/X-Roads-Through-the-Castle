package com.jesusc24.xroadsthroughthecastle.data.model

import java.io.Serializable

data class Bug(var nombre: String? = null,
               var tipo: String? = null,
               var gravedad: String? = null,
               var dispositivo: String? = null,
               var so: String? = null,
               var descripcion: String? = null,
               var estado: String? = null,
               var idUser: String? = null,
               var id: String? = null) : Serializable, Comparable<Bug> {

    companion object {
        const val TAG = "bug"
    }

    override fun compareTo(other: Bug): Int {
        return compareValuesBy(this, other, Bug::nombre, Bug::descripcion)
    }
}
