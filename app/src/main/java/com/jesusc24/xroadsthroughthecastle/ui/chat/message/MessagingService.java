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
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
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
        crearNotification(message);
    }

    public void crearNotification(RemoteMessage message) {
        PreferencesManager preferencesManager = new PreferencesManager(getApplicationContext());

        if(!(preferencesManager.getBoolean(Constants.KEY_AVAILABILITY)) && !preferencesManager.getString(Constants.KEY_FCM_TOKEN).equals("")) {
            Bundle bundle = new Bundle();
            Chat chat = new Chat();
            chat.setId(message.getData().get(Constants.KEY_FORO_ID));
            chat.setNombre(message.getData().get(Constants.KEY_NAME_FORO));
            bundle.putSerializable(Chat.TAG, chat);

            PendingIntent pendingIntent = new NavDeepLinkBuilder(getApplicationContext())
                    .setGraph(R.navigation.nav_graph)
                    .setDestination(R.id.messageFragment)
                    .setArguments(bundle)
                    .createPendingIntent();

            Notification.Builder builder = new Notification.Builder(getApplicationContext(), XRTCApplication.IDCHANNEL)
                    .setSmallIcon(R.drawable.ic_notification_menu)
                    .setContentTitle(message.getData().get(Constants.KEY_TITLE_NOTIFICATION) + message.getData().get(Constants.KEY_NAME_FORO))
                    .setContentText(message.getData().get(Constants.KEY_TEXT_NOTIFICATION) + message.getData().get(Constants.KEY_MESSAGE) + "\n" + message.getData().get(Constants.KEY_SEND_NOTIFICATION) + message.getData().get(Constants.KEY_NAME))
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(new Random().nextInt(1000), builder.build());
        }
    }
}
