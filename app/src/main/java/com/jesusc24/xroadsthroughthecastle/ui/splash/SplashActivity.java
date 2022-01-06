package com.jesusc24.xroadsthroughthecastle.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.model.User;
import com.jesusc24.xroadsthroughthecastle.ui.MainActivity;
import com.jesusc24.xroadsthroughthecastle.ui.login.LoginActivity;

/**
 * Activity que se muestra al principio de la aplicación
 */
public class SplashActivity extends AppCompatActivity {

    //Vamos a declarar una constante privada
    private static final long WAIT_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    /**
     * Vamos a simular un tiempo de espera con un hilo que duerme 2s y cuando despierta se ejecutara un méotdo
     * starLogin() que inicia la Activity Login
     */
    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(() ->  {
            if(saveSession()) {
                starApp();
            } else {
                startLogin();
            }
        }, WAIT_TIME);
    }

    private void startLogin(){
        startActivity(new Intent(this, LoginActivity.class));
        /*Voy a llamar de forma explícita al metodo finish() de una Activity, para eleminar
        esta activity de la pila de actividades, porque si el usuario pulsa BACK
        no queremos que se visualice*/
        finish();
    }

    /**
     * Método que comprueba si el usuario ha iniciado sesión y se ha guardado su email en el
     * fichero de preferencias DefaultSharePreferences
     * @return
     */
    private boolean saveSession() {
        return PreferenceManager.getDefaultSharedPreferences(this).contains(User.TAG);
    }

    private void starApp() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}