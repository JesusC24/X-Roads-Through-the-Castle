package com.jesusc24.xroadsthroughthecastle

import android.app.Application
import com.jesusc24.xroadsthroughthecastle.data.XRTCDatabase
import android.app.NotificationManager
import android.app.NotificationChannel
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager

class XRTCApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        //Se crea la base de datos en la primera ejecución de la aplicación
        XRTCDatabase.create(this)
    }

    /**
     * Este método crea el canal que se utilizará en las notificaciones
     * de la aplicación
     */
    private fun createNotificationChannel() {
        // Se crea una clase NotificationChannel sólo en el caso en el que que la API 26+
        //porque no se ha incluido en la librería de soporte

        //1. Difinir la importancia
        val importancia = NotificationManager.IMPORTANCE_DEFAULT
        //2. Definir el nombre del canal
        val nameChannel = getString(R.string.name_channel)
        //3. Se crea el canal
        val notificationChannel = NotificationChannel(IDCHANNEL, nameChannel, importancia)

        //3.1 OPCIONAL, configurar el modo de luces, sonido, vibración...
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        val tono = establecerSonido()
        if (tono != null) {
            val sound = Uri.parse(tono)
            val att = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build()
            notificationChannel.setSound(sound, att)
        }

        //4. Añadir este canal a NotificationManager
        getSystemService(NotificationManager::class.java).createNotificationChannel(
            notificationChannel
        )
    }

    private fun establecerSonido(): String? {
        val preferencesManager = PreferencesManager(applicationContext)
        return preferencesManager.getString(Constants.KEY_RIGNTONE_CHAT)
    }

    companion object {
        const val IDCHANNEL = "123456"
    }
}