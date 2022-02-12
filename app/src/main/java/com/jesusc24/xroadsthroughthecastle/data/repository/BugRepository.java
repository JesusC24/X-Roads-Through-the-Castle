package com.jesusc24.xroadsthroughthecastle.data.repository;

import com.jesusc24.xroadsthroughthecastle.data.XRTCDatabase;
import com.jesusc24.xroadsthroughthecastle.data.constantes.ConstBugs;
import com.jesusc24.xroadsthroughthecastle.data.dao.BugDAO;
import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryManageCallback;
import com.jesusc24.xroadsthroughthecastle.ui.bugs.BugListContract;
import com.jesusc24.xroadsthroughthecastle.ui.bugs.BugManagerContract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BugRepository implements BugListContract.Repository, BugManagerContract.Repository {
    public static BugRepository instace;
    private List<Bug> list;
    private BugDAO bugDAO;

    private BugRepository() {
        list = new ArrayList<>();
        bugDAO = XRTCDatabase.getDatabase().bugDAO();
    }

    public static BugRepository getInstance() {
        if(instace == null) {
            instace = new BugRepository();
        }

        initialize();
        return instace;
    }

    /**
     * Ejemplos para que se vean todas las funciones que tiene los bug
     */
    private static void initialize() {
        try {
            if(XRTCDatabase.databaseWriteExecutor.submit(() -> instace.bugDAO.hayBugs()).get().size() == 0) {
                Bug bug1 = new Bug("bug sin aceptar", ConstBugs.Tipo.OTRO, ConstBugs.Gravedad.CRITICO, ConstBugs.Dispositivo.PC, ConstBugs.SO.WINDOWS, "", ConstBugs.Estado.ENVIADO, 1);
                XRTCDatabase.databaseWriteExecutor.submit(() -> instace.bugDAO.insert(bug1));

                Bug bug2 = new Bug("bug aceptado pero sin resolver", ConstBugs.Tipo.LOGICA, ConstBugs.Gravedad.GRAVE, ConstBugs.Dispositivo.MAC, ConstBugs.SO.LINUX, "", ConstBugs.Estado.APROBADO, 2);
                XRTCDatabase.databaseWriteExecutor.submit(() -> instace.bugDAO.insert(bug2));

                Bug bug3 = new Bug("bug denegado", ConstBugs.Tipo.CONTENIDO, ConstBugs.Gravedad.INSIGNIFICANTE, ConstBugs.Dispositivo.PC, ConstBugs.SO.WINDOWS, "", ConstBugs.Estado.DENEGADO, 3);
                XRTCDatabase.databaseWriteExecutor.submit(() -> instace.bugDAO.insert(bug3));

                Bug bug4 = new Bug("bug arreglado", ConstBugs.Tipo.DISENO, ConstBugs.Gravedad.ESCASA, ConstBugs.Dispositivo.MOVIL, ConstBugs.SO.MAC_OS, "", ConstBugs.Estado.ARREGLADO, 4);
                XRTCDatabase.databaseWriteExecutor.submit(() -> instace.bugDAO.insert(bug4));
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getList(OnRepositoryListCallback callback) {
        try {
            list = XRTCDatabase.databaseWriteExecutor.submit(() -> bugDAO.select()).get();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        callback.onSuccess(list);
    }

    @Override
    public void delete(Bug bug, OnRepositoryListCallback callback) {
        XRTCDatabase.databaseWriteExecutor.submit(() -> bugDAO.delete(bug));
        callback.onDeleteSuccess("Se ha borrado con exito el bug " + bug.getNombre());
    }

    @Override
    public void undo(Bug bug, OnRepositoryListCallback callback) {
        XRTCDatabase.databaseWriteExecutor.submit(() -> bugDAO.insert(bug));
        callback.onUndoSuccess();
    }

    @Override
    public void add(Bug bug, OnRepositoryManageCallback callback) {
        XRTCDatabase.databaseWriteExecutor.submit(() -> bugDAO.insert(bug));
        callback.onAddSuccess("Se ha añadido el bug " + bug.getNombre() + " con exito");
    }

    @Override
    public void edit(Bug bug, OnRepositoryManageCallback callback) {
        XRTCDatabase.databaseWriteExecutor.submit(() -> bugDAO.update(bug));
        callback.onEditSucess("Se ha editado el bug " + bug.getNombre() + " con exito");
    }
}
