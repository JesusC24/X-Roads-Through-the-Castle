package com.jesusc24.xroadsthroughthecastle.utils;

import android.util.Patterns;

import com.jesusc24.xroadsthroughthecastle.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase no podrá tener clases hijos. No se puede heredar de ella
 * mediante el modificador  final
 */
public final class CommonUtils {
    static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$*?¡\\-_])(?!.*\\s).{8,20}$";
    //Todos sus métodos serán STATICS
    /**
     * Método que comprueba que la contraseña cumple los siguientes requisitos
     * - Debe contener al menos un dígito 0-9
     * - Debe contener al menos un carácter en mayúsucula
     * - Debe contener al menos un carácter en minúscula
     * - Su longitud máxima es de 8 y máxima 20
     * @return
     */
    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(Patterns.EMAIL_ADDRESS.toString());
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Para validar que solo contiene letras el string
     */
    public static int validarString(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {
            return R.string.validarDatos_string;
        }
        return 0;
    }
}
