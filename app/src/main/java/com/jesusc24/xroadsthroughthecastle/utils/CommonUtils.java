package com.jesusc24.xroadsthroughthecastle.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Patterns;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase no podrá tener clases hijos. No se puede heredar de ella
 * mediante el modificador  final
 */
public final class CommonUtils {
    static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$*?¡\\-_])(?!.*\\s).{8,20}$";
    static final String PASSSWORD_BUG_PATTERN = "^.{8,99}$";
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

    /**
     * Requisitos que tiene que tener una contraseña para poder ser un bug privado
     * @param password
     * @return
     */
    public static boolean isPasswordValidBug(String password) {
        Pattern pattern = Pattern.compile(PASSSWORD_BUG_PATTERN);
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

    public static String getSHA512(String input) {
        String result = null;

        try {
            MessageDigest digest = MessageDigest.getInstance(Constants.SHA_512);
            digest.reset();
            digest.update(input.getBytes(StandardCharsets.UTF_8));
            result = String.format("%0128x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String encodeImage(Bitmap bitmap) {
        if(bitmap != null) {
            int previewWidth = 150;
            int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
            Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        return null;
    }

    public static Bitmap decodeImage(String string) {
        if(string != null) {
            byte[] decodedString = Base64.decode(string, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }

        return null;
    }

    public static String getReadableDateTime(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }
}
