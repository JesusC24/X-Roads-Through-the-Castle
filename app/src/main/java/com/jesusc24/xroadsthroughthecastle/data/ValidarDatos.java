package com.jesusc24.xroadsthroughthecastle.data;

import java.util.regex.Pattern;

public class ValidarDatos {
    public static String validarString(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if(!patron.matcher(nombre).matches() || nombre.length() > 30) {
            return "Nombre invalido";
        } else {
            return null;
        }
    }

    public static String esPasswordValida(String password) {
        if(password.length()<8) {
            return "Contraseña incorrecta";
        } else {
            return null;
        }
    }

    public static String esPasswordIgual(String password, String confirmarPassword) {
        //TODO no va esta función para saber que las dos password sean iguales
        if(confirmarPassword.equals(password)) {
            return "Las contraseñas no son iguales";
        } else {
            return null;
        }
    }
}
