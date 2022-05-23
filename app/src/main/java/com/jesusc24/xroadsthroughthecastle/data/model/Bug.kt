package com.jesusc24.xroadsthroughthecastle.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * POJO Bug que se utiliza en la entidad bug
 */
@Entity(tableName = "bug")
data class Bug(@NonNull var nombre: String? = null,
               @NonNull var tipo: String? = null,
               @NonNull var gravedad: String? = null,
               @NonNull var dispositivo: String? = null,
               @NonNull var so: String? = null,
               var descripcion: String? = null,
               var estado: String? = null,
               @PrimaryKey(autoGenerate = true) var id: Int? = null) : Serializable, Comparable<Bug> {

    companion object {
        const val TAG = "bug"
    }

    override fun compareTo(other: Bug): Int {
        return compareValuesBy(this, other, Bug::nombre, Bug::descripcion)
    }
}
