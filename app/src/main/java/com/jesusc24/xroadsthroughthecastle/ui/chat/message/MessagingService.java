package com.jesusc24.xroadsthroughthecastle.ui.chat.message;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.NavDeepLinkBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.XRTCApplication;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager;

import java.util.Random;

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        PreferencesManager preferencesManager = new PreferencesManager(getApplicationContext());

        if(preferencesManager.getBoolean(Constants.KEY_AVAILABILITY)) {
            crearNotification(message);
        }
    }

    public void crearNotification(RemoteMessage message) {

        Bundle bundle = new Bundle();

        PendingIntent pendingIntent = new NavDeepLinkBuilder(getApplicationContext())
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.chatListFragment)
                .setArguments(bundle)
                .createPendingIntent();

        Notification.Builder builder = new Notification.Builder(getApplicationContext(), XRTCApplication.IDCHANNEL)
                .setSmallIcon(R.drawable.ic_notification_menu)
                .setContentTitle("Notificación del chat - " + message.getData().get(Constants.KEY_NAME_FORO))
                .setContentText("El mensaje es: " + message.getData().get(Constants.KEY_MESSAGE) + "\ny ha sido mandado por " + message.getData().get(Constants.KEY_NAME))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(new Random().nextInt(1000), builder.build());
    }
}
