package com.jesusc24.xroadsthroughthecastle.data.constantes

/**
 * Clase de costantes que van asociadas a la entidad de BUG
 * Se utilizará para a la hora de coger el valor de los diferentes Spinners.
 * Cada subclase pertenece a un Spinner diferente
 */
class ConstBugs {
    /**
     * Clase de constantes para el spinner tipo de la entidad bug
     */
    object Tipo {
        const val TOTAL = 4
        const val DISENO = "Diseño"
        const val LOGICA = "Lógica"
        const val CONTENIDO = "Contenido"
        const val OTRO = "Otro"
    }

    /**
     * Clase de constantes para el spinner gravedad de la entidad bug
     */
    object Gravedad {
        const val TOTAL = 5
        const val INSIGNIFICANTE = "Insignificante"
        const val ESCASA = "Escasa"
        const val NORMAL = "Normal"
        const val GRAVE = "Grave"
        const val CRITICO = "Crítico"
    }

    /**
     * Clase de constantes para el spinner dispositivo de la entidad bug
     */
    object Dispositivo {
        const val TOTAL = 4
        const val PC = "PC"
        const val MAC = "Mac"
        const val MOVIL = "Móvil"
        const val TABLET = "Tablet"
    }

    /**
     * Clase de constantes para el spinner Sistema Operativo (SO) de la entidad bug
     */
    object SO {
        const val TOTAL = 5
        const val WINDOWS = "Windows"
        const val MAC_OS = "MAC_OS"
        const val LINUX = "Linux"
        const val ANDROID = "Android"
        const val IOS = "IOS"
    }

    object Estado {
        const val TOTAL = 4
        const val ENVIADO = "C_Enviado"
        const val APROBADO = "B_Aprobado"
        const val ARREGLADO = "A_Arreglado"
        const val DENEGADO = "Denegado"
    }
}