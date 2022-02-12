package com.jesusc24.xroadsthroughthecastle.data.constantes;

/**
 * Clase de costantes que van asociadas a la entidad de BUG
 * Se utilizará para a la hora de coger el valor de los diferentes Spinners.
 * Cada subclase pertenece a un Spinner diferente
 */
public final class ConstBugs {
    /**
     * Clase de constantes para el spinner tipo de la entidad bug
     */
    public static class Tipo {
        public final static int TOTAL = 4;
        public final static String DISENO = "Diseño";
        public final static String LOGICA = "Lógica";
        public final static String CONTENIDO = "Contenido";
        public final static String OTRO  = "Otro";
    }
    /**
     * Clase de constantes para el spinner gravedad de la entidad bug
     */
    public static class Gravedad {
        public final static int TOTAL = 5;
        public final static String INSIGNIFICANTE = "Insignificante";
        public final static String ESCASA = "Escasa";
        public final static String NORMAL = "Normal";
        public final static String GRAVE = "Grave";
        public final static String CRITICO = "Crítico";
    }

    /**
     * Clase de constantes para el spinner dispositivo de la entidad bug
     */
    public static class Dispositivo {
        public final static int TOTAL = 4;
        public final static String PC = "PC";
        public final static String MAC = "Mac";
        public final static String MOVIL = "Móvil";
        public final static String TABLET = "Tablet";
    }

    /**
     * Clase de constantes para el spinner Sistema Operativo (SO) de la entidad bug
     */
    public static class SO {
        public final static int TOTAL = 5;
        public final static String WINDOWS = "Windows";
        public final static String MAC_OS = "MAC_OS";
        public final static String LINUX = "Linux";
        public final static String ANDROID = "Android";
        public final static String IOS = "IOS";
    }

    public static class Estado {
        public final static int TOTAL = 4;
        public final static String ENVIADO = "C_Enviado";
        public final static String APROBADO = "B_Aprobado";
        public final static String ARREGLADO = "A_Arreglado";
        public final static String DENEGADO = "Denegado";
    }

}
