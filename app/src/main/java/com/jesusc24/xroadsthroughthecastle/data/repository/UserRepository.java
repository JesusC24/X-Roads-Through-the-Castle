package com.jesusc24.xroadsthroughthecastle.data.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.model.User;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryCallback;
import com.jesusc24.xroadsthroughthecastle.ui.login.LoginActivity;
import com.jesusc24.xroadsthroughthecastle.ui.singUp.SignUpActivity;
import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils;
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager;

import java.util.HashMap;

public class UserRepository {

    private static final String TAG = UserRepository.class.getName(); //Imprime el nombre de la clase
    private static final int GOOGLE_SING_IN = 100;
    private static PreferencesManager preferenceManager;
    private static UserRepository instance;
    private static OnRepositoryCallback callback;
    private static FirebaseFirestore database;

    public static UserRepository getInstance(OnRepositoryCallback activity) {
        if(instance == null) {
            instance = new UserRepository();
            database = FirebaseFirestore.getInstance();
        }

        callback = activity;
        return instance;
    }

    public void login(User user) {
        preferenceManager = new PreferencesManager(LoginActivity.context);

        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, user.getEmail())
                .whereEqualTo(Constants.KEY_PASSWORD, CommonUtils.getSHA512(user.getPassword()))
                .get()

                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                        preferenceManager.putString(Constants.KEY_EMAIL, documentSnapshot.getString(Constants.KEY_EMAIL));
                        preferenceManager.putString(Constants.KEY_PASSWORD, documentSnapshot.getString(Constants.KEY_PASSWORD));
                        callback.onSuccess();
                    } else {
                        callback.onFailure(R.string.err_auth);
                    }
                });

    }


    public void signUp(User user) {
        HashMap<String, Object> newUser = new HashMap<>();
        newUser.put(Constants.KEY_NAME, user.getName());
        newUser.put(Constants.KEY_EMAIL, user.getEmail());
        newUser.put(Constants.KEY_PASSWORD, CommonUtils.getSHA512(user.getPassword()));
        newUser.put(Constants.KEY_IMAGE, user.getImage());

        preferenceManager = new PreferencesManager(SignUpActivity.context);

        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, user.getEmail())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        if(task.getResult().isEmpty()) {
                            database.collection(Constants.KEY_COLLECTION_USERS)
                                    .add(newUser)

                                    .addOnSuccessListener(documentReference -> {
                                        preferenceManager.putString(Constants.KEY_USER_ID, documentReference.getId());
                                        preferenceManager.putString(Constants.KEY_NAME, user.getName());
                                        preferenceManager.putString(Constants.KEY_IMAGE, user.getImage());
                                        preferenceManager.putString(Constants.KEY_EMAIL, user.getEmail());
                                        callback.onSuccess();
                                    })

                                    .addOnFailureListener(exception -> callback.onFailure(R.string.err_auth));
                        } else {
                            callback.onFailure(R.string.err_emailRepeat);
                        }
                    }
                })

                .addOnFailureListener(exception -> callback.onFailure(R.string.err_auth));

    }

    public void deleteUser(String idUser) {
        database.collection(Constants.KEY_COLLECTION_USERS)
                .document(idUser)
                .delete()
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(R.string.err_deleteAccount));
    }

    public void updateNameUser(String name, String idUser) {
        HashMap<String, Object> newUser = new HashMap<>();
        newUser.put(Constants.KEY_NAME, name);

        database.collection(Constants.KEY_COLLECTION_USERS)
                .document(idUser)
                .update(newUser)

                .addOnFailureListener(exception -> callback.onFailure(R.string.err_changeUser));
    }

    public void updatePassword(String password, String idUser) {
        HashMap<String, Object> newUser = new HashMap<>();
        newUser.put(Constants.KEY_PASSWORD, CommonUtils.getSHA512(password));

        database.collection(Constants.KEY_COLLECTION_USERS)
                .document(idUser)
                .update(newUser)

                .addOnFailureListener(exception -> callback.onFailure(R.string.err_changePassword));
    }

    public void changeImage(String image, String idUser) {
        HashMap<String, Object> newUser = new HashMap<>();
        newUser.put(Constants.KEY_IMAGE, image);

        database.collection(Constants.KEY_COLLECTION_USERS)
                .document(idUser)
                .update(newUser)

                .addOnFailureListener(exception -> callback.onFailure(R.string.err_changeImage));
    }

    public void deleteFCM(String idUser) {
        HashMap<String, Object> newUser = new HashMap<>();
        newUser.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());

        database.collection(Constants.KEY_COLLECTION_USERS)
                .document(idUser)
                .update(newUser)

                .addOnCompleteListener(task -> {
                    //database.collection(Constants.KEY_COLLECTION_FORO)
                })

                .addOnFailureListener(exception -> callback.onFailure(R.string.err_closeSesion));

    }

    /*public void firebaseAuthWithGoogle(String idToken, Activity activity) {
        GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(idToken)
                .requestEmail()
                .build();

        GoogleSignInClient googleCliente = GoogleSignIn.getClient(activity, googleConf);

        activity.startActivityForResult(googleCliente.getSignInIntent(), GOOGLE_SING_IN);
    }


    public void resultGoogle(int requestCode, int resultCode, Intent data) {
        if(requestCode == GOOGLE_SING_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            GoogleSignInAccount acount = task.getResult();

            User user = new User(acount.getDisplayName(), acount.getEmail());

            if(acount != null) {
                AuthCredential credential = GoogleAuthProvider.getCredential(acount.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()) {
                        //callback.onSuccess("Logeado con exito", user);
                    }
                });
            }
        }
    }*/
}
