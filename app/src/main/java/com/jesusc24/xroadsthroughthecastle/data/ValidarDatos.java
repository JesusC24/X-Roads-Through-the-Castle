package com.jesusc24.xroadsthroughthecastle.data;

import android.util.Patterns;

import com.jesusc24.xroadsthroughthecastle.R;

import java.util.regex.Pattern;

/**
 * Clase que sirve para validar diferentes tipos de datos
 */
public class ValidarDatos {
    /**
     * Para validar que solo contiene letras el string
     */
    public static int validarString(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if(!patron.matcher(nombre).matches() || nombre.length() > 30) {
            return R.string.validarDatos_string;
        }
        return 0;
    }

    /**
     * Para validar que la contraseña es valida
     */
    public static int esPasswordValida(String password) {
        if(password.length()<8) {
            return R.string.validarDatos_password;
        }
        return 0;
    }

    /**
     * Para validar que dos string son exactamente iguales en contenido
     */
    public static int esPasswordIgual(String password, String confirmarPassword) {
        //TODO no va esta función para saber que las dos password sean iguales
        if(confirmarPassword.contentEquals(password)) {
            return R.string.validarDatos_paswordIgual;
        }
        return 0;
    }

    /**
     * Para validar que un email tenga el formato correcto
     */
    public static int validarEmail(String email) {
        //TODO no funciona validarEmail
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if(pattern.matcher(email).matches()) {
            return R.string.validarDatos_email;
        }
        return 0;
    }
}
