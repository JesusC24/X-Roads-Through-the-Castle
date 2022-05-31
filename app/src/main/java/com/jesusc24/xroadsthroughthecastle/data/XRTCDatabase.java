package com.jesusc24.xroadsthroughthecastle.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jesusc24.xroadsthroughthecastle.data.dao.ChatDAO;
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//1. Definimos la configuración de la base de datos
@Database(entities = {Chat.class}, version = 1)
public abstract class XRTCDatabase extends RoomDatabase {
    //2. Crear los métodos de obtención de los DAO.
    public abstract ChatDAO chatDAO();

    private static volatile XRTCDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static void create(final Context context) {
        if (INSTANCE == null) {
            synchronized (XRTCDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            XRTCDatabase.class, "xrtc")
                            .fallbackToDestructiveMigration() //esto es solo para clase, ya que destruye la base de datos antigua
                            .build();
                }
            }
        }
    }

    public static XRTCDatabase getDatabase() {
        return INSTANCE;
    }
}
