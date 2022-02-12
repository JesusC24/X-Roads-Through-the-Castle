package com.jesusc24.xroadsthroughthecastle.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jesusc24.xroadsthroughthecastle.data.constantes.ConstBugs;
import com.jesusc24.xroadsthroughthecastle.data.dao.BugDAO;
import com.jesusc24.xroadsthroughthecastle.data.dao.ChatDAO;
import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//1. Definimos la configuración de la base de datos
@Database(entities = {Chat.class, Bug.class}, version = 1)
public abstract class XRTCDatabase extends RoomDatabase {
    //2. Crear los métodos de obtención de los DAO.
    public abstract ChatDAO chatDAO();
    public abstract BugDAO bugDAO();

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
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    databaseWriteExecutor.execute(() -> prepopulate(context));
                                }
                            })
                            .build();
                }
            }
        }
    }


    private static void prepopulate(Context context) {
        BugDAO bugDAO = INSTANCE.bugDAO();

        Bug bug1 = new Bug("bug sin aceptar", ConstBugs.Tipo.OTRO, ConstBugs.Gravedad.CRITICO, ConstBugs.Dispositivo.PC, ConstBugs.SO.WINDOWS, "", ConstBugs.Estado.ENVIADO, 1);
        Bug bug2 = new Bug("bug aceptado pero sin resolver", ConstBugs.Tipo.LOGICA, ConstBugs.Gravedad.GRAVE, ConstBugs.Dispositivo.MAC, ConstBugs.SO.LINUX, "", ConstBugs.Estado.APROBADO, 2);
        Bug bug3 = new Bug("bug denegado", ConstBugs.Tipo.CONTENIDO, ConstBugs.Gravedad.INSIGNIFICANTE, ConstBugs.Dispositivo.PC, ConstBugs.SO.WINDOWS, "", ConstBugs.Estado.DENEGADO, 3);
        Bug bug4 = new Bug("bug arreglado", ConstBugs.Tipo.DISENO, ConstBugs.Gravedad.ESCASA, ConstBugs.Dispositivo.MOVIL, ConstBugs.SO.MAC_OS, "", ConstBugs.Estado.ARREGLADO, 4);

        getDatabase().runInTransaction(() -> {
            bugDAO.insert(bug1);
            bugDAO.insert(bug2);
            bugDAO.insert(bug3);
            bugDAO.insert(bug4);
        });
    }

    public static XRTCDatabase getDatabase() {
        return INSTANCE;
    }
}
