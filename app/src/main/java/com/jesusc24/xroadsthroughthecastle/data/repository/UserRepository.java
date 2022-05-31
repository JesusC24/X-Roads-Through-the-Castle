package com.jesusc24.xroadsthroughthecastle.data.repository;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.model.User;
import com.jesusc24.xroadsthroughthecastle.ui.login.LoginActivity;
import com.jesusc24.xroadsthroughthecastle.ui.login.LoginViewModel;
import com.jesusc24.xroadsthroughthecastle.ui.singUp.SignUpActivity;
import com.jesusc24.xroadsthroughthecastle.ui.singUp.SignUpViewModel;
import com.jesusc24.xroadsthroughthecastle.utils.PreferenceManager;

import java.util.HashMap;

public class UserRepository {

    private static final String TAG = UserRepository.class.getName(); //Imprime el nombre de la clase
    private static final int GOOGLE_SING_IN = 100;
    private static PreferenceManager preferenceManager;
    private static UserRepository instance;
    private static ViewModel callback;

    public static UserRepository getInstance(ViewModel activity) {
        if(instance == null) {
            instance = new UserRepository();
        }

        callback = activity;
        return instance;
    }

    public void login(User user) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(LoginActivity.context);

        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL, user.getEmail())
                .whereEqualTo(Constants.KEY_PASSWORD, user.getPassword())
                .get()

                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE));
                        preferenceManager.putString(Constants.KEY_EMAIL, documentSnapshot.getString(Constants.KEY_EMAIL));
                        ((LoginViewModel)callback).onSuccess();
                    } else {
                        ((LoginViewModel)callback).onFailure(R.string.err_auth);
                    }
                });

    }


    public void signUp(User user) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> newUser = new HashMap<>();
        newUser.put(Constants.KEY_NAME, user.getName());
        newUser.put(Constants.KEY_EMAIL, user.getEmail());
        newUser.put(Constants.KEY_PASSWORD, user.getPassword());
        newUser.put(Constants.KEY_IMAGE, user.getImage());

        preferenceManager = new PreferenceManager(SignUpActivity.context);

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
                                        ((SignUpViewModel)callback).onSuccess();
                                    })

                                    .addOnFailureListener(exception -> ((SignUpViewModel)callback).onFailure(R.string.err_auth));
                        } else {
                            ((SignUpViewModel)callback).onFailure(R.string.err_emailRepeat);
                        }
                    }
                })

                .addOnFailureListener(exception -> ((SignUpViewModel)callback).onFailure(R.string.err_auth));

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
