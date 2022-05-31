package com.jesusc24.xroadsthroughthecastle.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryManageCallback;
import com.jesusc24.xroadsthroughthecastle.ui.bugs.BugListContract;
import com.jesusc24.xroadsthroughthecastle.ui.bugs.BugManagerContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BugRepository implements BugListContract.Repository, BugManagerContract.Repository {
    public static BugRepository instace;
    private List<Bug> list;

    private BugRepository() {
        list = new ArrayList<>();
    }

    public static BugRepository getInstance() {
        if(instace == null) {
            instace = new BugRepository();
        }

        return instace;
    }

    @Override
    public void getList(OnRepositoryListCallback callback) {
        list = new ArrayList<>();

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_BUGS)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null) {
                        for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                            Bug bug = new Bug();
                            bug.setDescripcion(queryDocumentSnapshot.getString(Constants.KEY_DESCRIPTION));
                            bug.setNombre(queryDocumentSnapshot.getString(Constants.KEY_NAME));
                            bug.setTipo(queryDocumentSnapshot.getString(Constants.KEY_TYPE));
                            bug.setDispositivo(queryDocumentSnapshot.getString(Constants.KEY_DISPOSITIVO));
                            bug.setEstado(queryDocumentSnapshot.getString(Constants.KEY_ESTADO));
                            bug.setGravedad(queryDocumentSnapshot.getString(Constants.KEY_GRAVEDAD));
                            bug.setSo(queryDocumentSnapshot.getString(Constants.KEY_SO));
                            bug.setId(queryDocumentSnapshot.getId());
                            list.add(bug);
                        }
                    }

                    callback.onSuccess(list);
                });
    }

    @Override
    public void delete(Bug bug, OnRepositoryListCallback callback) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_BUGS)
                .document(bug.getId())
                .delete()
                .addOnSuccessListener(unused -> callback.onDeleteSuccess(bug.getNombre()))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void undo(Bug bug, OnRepositoryListCallback callback) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> newBug = createHashMap(bug);

        database.collection(Constants.KEY_COLLECTION_BUGS)
                .add(newBug)

                .addOnSuccessListener(documentReference -> {
                    callback.onUndoSuccess();
                })

                .addOnFailureListener(exception -> callback.onFailure(exception.getMessage()));
    }

    @Override
    public void add(Bug bug, OnRepositoryManageCallback callback) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> newBug = createHashMap(bug);

        database.collection(Constants.KEY_COLLECTION_BUGS)
                .add(newBug)

                .addOnSuccessListener(documentReference -> {
                    callback.onAddSuccess("Se ha añadido el bug " + bug.getNombre() + " con exito");
                })

                .addOnFailureListener(exception -> callback.onFailure(exception.getMessage()));
    }

    @Override
    public void edit(Bug bug, OnRepositoryManageCallback callback) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> newBug = createHashMap(bug);

        database.collection(Constants.KEY_COLLECTION_BUGS)
                .document(bug.getId())
                .update(newBug)

                .addOnSuccessListener(documentReference -> {
                    callback.onEditSucess("Se ha editado el bug " + bug.getNombre() + " con exito");
                })

                .addOnFailureListener(exception -> callback.onFailure(exception.getMessage()));
    }

    private static HashMap<String, Object> createHashMap(Bug bug) {
        HashMap<String, Object> newBug = new HashMap<>();
        newBug.put(Constants.KEY_DESCRIPTION, bug.getDescripcion());
        newBug.put(Constants.KEY_NAME, bug.getNombre());
        newBug.put(Constants.KEY_TYPE, bug.getTipo());
        newBug.put(Constants.KEY_DISPOSITIVO, bug.getDispositivo());
        newBug.put(Constants.KEY_ESTADO, bug.getEstado());
        newBug.put(Constants.KEY_GRAVEDAD, bug.getGravedad());
        newBug.put(Constants.KEY_SO, bug.getSo());
        return newBug;
    }
}
