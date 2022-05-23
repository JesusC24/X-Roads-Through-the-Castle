package com.jesusc24.xroadsthroughthecastle;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.navigation.NavDeepLinkBuilder;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.ui.MainActivity;

import java.util.Random;

public class EjemploIntentPersonalizado extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bRecibido = intent.getExtras();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Chat.TAG, bRecibido.getSerializable(Chat.TAG));
        bundle.putSerializable(MainActivity.TAG, "");

        PendingIntent pendingIntent = new NavDeepLinkBuilder(context)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.messageFragment)
                .setArguments(bundle)
                .createPendingIntent();

        //5. Crear la notificación
        Notification.Builder builder = new Notification.Builder(context, XRTCApplication.IDCHANNEL)
                .setSmallIcon(R.drawable.ic_add_alert)
                .setContentTitle(bRecibido.getString("title"))
                .setContentText(bRecibido.getString("message"))
                .setContentIntent(pendingIntent);

        //6. Añadir la notificación al Manager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(new Random().nextInt(1000), builder.build());
    }
}
