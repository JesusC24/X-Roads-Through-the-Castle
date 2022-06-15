package com.jesusc24.xroadsthroughthecastle.utils

import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Patterns
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Esta clase no podrá tener clases hijos. No se puede heredar de ella
 * mediante el modificador  final
 */
object CommonUtils {
    const val PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$*?¡\\-_])(?!.*\\s).{8,20}$"
    const val PASSSWORD_BUG_PATTERN = "^.{8,99}$"
    //Todos sus métodos serán STATICS
    /**
     * Método que comprueba que la contraseña cumple los siguientes requisitos
     * - Debe contener al menos un dígito 0-9
     * - Debe contener al menos un carácter en mayúsucula
     * - Debe contener al menos un carácter en minúscula
     * - Su longitud máxima es de 8 y máxima 20
     * @return
     */
    @JvmStatic
    fun isPasswordValid(password: String?): Boolean {
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

    /**
     * Requisitos que tiene que tener una contraseña para poder ser un bug privado
     * @param password
     * @return
     */
    @JvmStatic
    fun isPasswordValidBug(password: String?): Boolean {
        val pattern = Pattern.compile(PASSSWORD_BUG_PATTERN)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

    fun isEmailValid(email: String?): Boolean {
        val pattern = Pattern.compile(Patterns.EMAIL_ADDRESS.toString())
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    @JvmStatic
    fun getSHA512(input: String): String? {
        var result: String? = null
        try {
            val digest = MessageDigest.getInstance(Constants.SHA_512)
            digest.reset()
            digest.update(input.toByteArray(StandardCharsets.UTF_8))
            result = String.format("%0128x", BigInteger(1, digest.digest()))
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return result
    }

    @JvmStatic
    fun encodeImage(bitmap: Bitmap?): String? {
        if (bitmap != null) {
            val previewWidth = 150
            val previewHeight = bitmap.height * previewWidth / bitmap.width
            val previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false)
            val byteArrayOutputStream = ByteArrayOutputStream()
            previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
            val bytes = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(bytes, Base64.DEFAULT)
        }
        return null
    }

    @JvmStatic
    fun decodeImage(string: String?): Bitmap? {
        if (string != null) {
            val decodedString = Base64.decode(string, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        }
        return null
    }

    @JvmStatic
    fun getReadableDateTime(date: Date?): String {
        return SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date)
    }
}