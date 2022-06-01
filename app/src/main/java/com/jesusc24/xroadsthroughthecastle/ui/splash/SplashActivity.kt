package com.jesusc24.xroadsthroughthecastle.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.jesusc24.xroadsthroughthecastle.R
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants
import com.jesusc24.xroadsthroughthecastle.ui.MainActivity
import com.jesusc24.xroadsthroughthecastle.ui.login.LoginActivity
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager

class SplashActivity : AppCompatActivity() {
    //Vamos a declarar una constante privada
    private val WAIT_TIME: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    /**
     * Vamos a simular un tiempo de espera con un hilo que duerme 2s y cuando despierta se ejecutara un méotdo
     * starLogin() que inicia la Activity Login
     */
    override fun onStart() {
        super.onStart()
        val r = Runnable {
            if (saveSession()) {
                starApp()
            } else {
                startLogin()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed(r, WAIT_TIME)
    }

    private fun startLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        /*Voy a llamar de forma explícita al método finish() de una Activity, para eliminar
        esta activity de la pila de actividades, porque si el usuario pulsa BACK
        no queremos que se visualice*/
        finish()
    }

    /**
     * Método que comprueba si el usuario ha iniciado sesión y se ha guardado su email en el
     * fichero de preferencias DefaultSharePreferences
     * @return
     */
    private fun saveSession(): Boolean {
        return PreferencesManager(
            applicationContext
        ).getBoolean(Constants.REMEMBER_USER)
    }

    private fun starApp() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}