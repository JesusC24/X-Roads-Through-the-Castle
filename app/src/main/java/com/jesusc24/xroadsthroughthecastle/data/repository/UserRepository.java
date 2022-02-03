package com.jesusc24.xroadsthroughthecastle.data.repository;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.jesusc24.xroadsthroughthecastle.data.model.User;
import com.jesusc24.xroadsthroughthecastle.ui.base.Event;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryCallback;
import com.jesusc24.xroadsthroughthecastle.ui.login.LoginContract;
import com.jesusc24.xroadsthroughthecastle.ui.singUp.SignUpContract;

import org.greenrobot.eventbus.EventBus;

public class UserRepository implements SignUpContract.Repository, LoginContract.Repository{

    private static final String  TAG = UserRepository.class.getName(); //Imprime el nombre de la clase
    private static final int GOOGLE_SING_IN = 100;

    private static UserRepository instance;
    private OnRepositoryCallback callback;

    public static UserRepository getInstance(OnRepositoryCallback listener) {
        if(instance==null) {
            instance = new UserRepository();
        }
        instance.callback = listener;
        return instance;
    }

    @Override
    public void login(User user) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            callback.onSuccess("usuario correcto");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //callback.onFailure("Error autenticación" + task.getException().toString());
                            //Se crea un evento mediante EventBus
                            Event loginEvent = new Event();
                            loginEvent.setEventType(Event.onLoginError);
                            loginEvent.setMessage(task.getException().toString());
                            //Publica un evento mediante el método post()
                            EventBus.getDefault().post(loginEvent);
                        }
                    }
                });
    }



    @Override
    public void signUp(User user) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            callback.onSuccess("usuario creado");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //Se crea un evento mediante EventBus
                            Event loginEvent = new Event();
                            loginEvent.setEventType(Event.onSingUpError);
                            loginEvent.setMessage(task.getException().toString());
                            //Publica un evento mediante el método post()
                            EventBus.getDefault().post(loginEvent);
                        }
                    }
                });
    }

    @Override
    public void firebaseAuthWithGoogle(String idToken, Activity activity) {
        GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(idToken)
                .requestEmail()
                .build();

        GoogleSignInClient googleCliente = GoogleSignIn.getClient(activity, googleConf);

        activity.startActivityForResult(googleCliente.getSignInIntent(), GOOGLE_SING_IN);
    }

    @Override
    public void resultGoogle(int requestCode, int resultCode, Intent data) {
        if(requestCode == GOOGLE_SING_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            GoogleSignInAccount acount = task.getResult();

            if(acount != null) {
                AuthCredential credential = GoogleAuthProvider.getCredential(acount.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()) {
                        callback.onSuccess("Logeado con exito");
                    }
                });
            }
        }
    }

    @Override
    public void firebaseAuthWithFacebook(String idToken) {

    }




}
