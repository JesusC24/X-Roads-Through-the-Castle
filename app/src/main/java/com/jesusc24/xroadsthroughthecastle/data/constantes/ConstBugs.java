package com.jesusc24.xroadsthroughthecastle.data.constantes;

/**
 * Clase de costantes que van asociadas a la entidad de BUG
 * Se utilizará para a la hora de coger el valor de los diferentes Spinners.
 * Cada subclase pertenece a un Spinner diferente
 * TODO No se encuentra implementado, se añadirán a la hora de implimentar los datos reales
 */
public class ConstBugs {
    /**
     * Clase de constantes para el spinner tipo de la entidad bug
     */
    public static class Tipo {
        public final static String DISENO = "Diseño";
        public final static String LOGICA = "Diseño";
        public final static String CONTENIDO = "Diseño";
        public final static String OTRO  = "Otro";
    }
    /**
     * Clase de constantes para el spinner gravedad de la entidad bug
     */
    public static class Gravedad {
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
        public final static String PC = "PC";
        public final static String MAC = "Mac";
        public final static String MOVIL = "Móvil";
        public final static String TABLET = "Tablet";
    }

    /**
     * Clase de constantes para el spinner Sistema Operativo (SO) de la entidad bug
     */
    public static class SO {
        public final static String WINDOWS = "Windows";
        public final static String MAC_OS = "MAC_OS";
        public final static String LINUX = "Linux";
        public final static String ANDROID = "Android";
        public final static String IOS = "IOS";
    }

}
