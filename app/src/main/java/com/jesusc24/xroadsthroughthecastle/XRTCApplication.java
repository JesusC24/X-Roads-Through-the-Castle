package com.jesusc24.xroadsthroughthecastle;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.jesusc24.xroadsthroughthecastle.data.XRTCDatabase;

public class XRTCApplication extends Application {

    public static final String IDCHANNEL = "123456";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        //Se crea la base de datos en la primera ejecución de la aplicación
        XRTCDatabase.create(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * Este método crea el canal que se utilizará en las notificaciones
     * de la aplicación
     */
    private void createNotificationChannel() {
        // Se crea una clase NotificationChannel sólo en el caso en el que que la API 26+
        //porque no se ha incluido en la librería de soporte

        //1. Difinir la importancia
        int importancia = NotificationManager.IMPORTANCE_DEFAULT;
        //2. Definir el nombre del canal
        String nameChannel = getString(R.string.name_channel);
        //3. Se crea el canal
        NotificationChannel notificationChannel = new NotificationChannel(IDCHANNEL, nameChannel, importancia);

        //3.1 OPCIONAL, configurar el modo de luces, sonido, vibración...
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.GREEN);

        /*String tono = establecerSonido();

        if(tono != null ){
            Uri sound = Uri.parse(tono);
            //notificationChannel.setSound(sound, AudioAttributes.);
        }*/

        //4. Añadir este canal a NotificationManager
        getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
    }

    private String establecerSonido() {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        if(!(sharedPreferences.getString(getString(R.string.key_ringtone_chat), "").equals(""))) {
            String tono = sharedPreferences.getString(getString(R.string.key_ringtone_chat), "");
            String[] list = getResources().getStringArray(R.array.ringtone_values);
            for (String s : list) {
                if (tono.contains(s)) {
                    return s;
                }
            }
        }

        return null;
    }

}
