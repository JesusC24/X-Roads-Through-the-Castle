package com.jesusc24.xroadsthroughthecastle.data.repository;

import com.jesusc24.xroadsthroughthecastle.data.constantes.ConstBugs;
import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryManageCallback;
import com.jesusc24.xroadsthroughthecastle.ui.bugs.BugListContract;
import com.jesusc24.xroadsthroughthecastle.ui.bugs.BugManagerContract;

import java.util.ArrayList;

public class BugRepositoryStatic implements BugListContract.Repository, BugManagerContract.Repository {
    public static BugRepositoryStatic instace;
    private ArrayList<Bug> list;

    private BugRepositoryStatic() {
        list = new ArrayList<>();
        intialice();
    }

    public static BugRepositoryStatic getInstance() {
        if(instace == null) {
            instace = new BugRepositoryStatic();
        }

        return instace;
    }

    private void intialice() {
        list.add(new Bug("bug 1", ConstBugs.Tipo.CONTENIDO, ConstBugs.Gravedad.ESCASA, ConstBugs.Dispositivo.MOVIL, ConstBugs.SO.LINUX, ConstBugs.Estado.ENVIADO, "mundo", 0));
        list.add(new Bug("bug 2", ConstBugs.Tipo.DISENO, ConstBugs.Gravedad.INSIGNIFICANTE, ConstBugs.Dispositivo.MAC, ConstBugs.SO.ANDROID, ConstBugs.Estado.APROBADO, "mundo", 0));
        list.add(new Bug("bug 3", ConstBugs.Tipo.LOGICA, ConstBugs.Gravedad.GRAVE, ConstBugs.Dispositivo.TABLET, ConstBugs.SO.IOS, ConstBugs.Estado.ARREGLADO, "mundo", 0));
        list.add(new Bug("bug 4", ConstBugs.Tipo.OTRO, ConstBugs.Gravedad.CRITICO, ConstBugs.Dispositivo.PC, ConstBugs.SO.WINDOWS, ConstBugs.Estado.DENEGADO, "mundo", 0));
    }

    @Override
    public void getList(OnRepositoryListCallback callback) {
        callback.onSuccess(list);
    }

    @Override
    public void delete(Bug bug, OnRepositoryListCallback callback) {
        list.remove(bug);
        callback.onDeleteSuccess("Se ha borrado con exito el bug " + bug.getNombre());
    }

    @Override
    public void undo(Bug bug, OnRepositoryListCallback callback) {
        list.add(bug);
        callback.onUndoSuccess("Se ha recuperado el bug " + bug.getNombre());
    }

    @Override
    public void add(Bug bug, OnRepositoryManageCallback callback) {
        list.add(bug);
        callback.onAddSuccess("Se ha añadido el bug " + bug.getNombre() + " con exito");
    }

    @Override
    public void edit(Bug bug, OnRepositoryManageCallback callback) {
        int posicionEliminar = 0;
        for(Bug b : list) {
            if(b.getNombre().equals(b.getNombre())) {
                posicionEliminar = list.indexOf(b);
            }
        }

        list.remove(posicionEliminar);
        list.add(bug);

        callback.onEditSucess("Se ha editado el chat" + bug.getNombre() + " con exito");
    }
}
