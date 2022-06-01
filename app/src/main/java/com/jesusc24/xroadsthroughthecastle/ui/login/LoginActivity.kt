package com.jesusc24.xroadsthroughthecastle.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants
import com.jesusc24.xroadsthroughthecastle.databinding.ActivityLoginBinding
import com.jesusc24.xroadsthroughthecastle.ui.MainActivity
import com.jesusc24.xroadsthroughthecastle.ui.singUp.SignUpActivity
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager

class LoginActivity : AppCompatActivity() {
    private val loginViewModel = LoginViewModel()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferenceManager: PreferencesManager
    companion object {
        lateinit var context : Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = applicationContext
        preferenceManager =
            PreferencesManager(context)

        //Inicializar Data Binding
        binding.viewmodel = loginViewModel
        //Para que aplique los cambios
        binding.executePendingBindings()
        //Para que Data Binding conozca el ciclo de vida del Fragment
        binding.lifecycleOwner = this
        binding.btSingUp.setOnClickListener {startSingUpActivity()}

        //Creamos el observador
        loginViewModel.state.observe(this) {
            resetError()
            when (it) {
                is State.EmailError -> setEmailError(it.message)
                is State.PasswordError -> setPasswordError(it.message)
                is State.AuthError ->  {
                    setProgressBar(false)
                    setToast(it.message)
                }
                is State.Loading ->  setProgressBar(it.process)
                is State.Success ->  {
                    setProgressBar(false)
                    startMainActivity()
                }
            }
        }
    }


    private fun setToast(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setPasswordError(message: Int) {
        binding.tilPassword.error = resources.getString(message)
    }

    private fun setEmailError(message: Int) {
        binding.tilEmail.error = resources.getString(message)
    }

    private fun setProgressBar(process: Boolean) {
        if(process) {
            binding.includeProgressbar.llProgressBar.visibility = View.VISIBLE
        } else {
            binding.includeProgressbar.llProgressBar.visibility  = View.INVISIBLE
        }
    }

    private fun startSingUpActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun startMainActivity() {
        if (binding.chxRemember.isChecked) {
            preferenceManager.putBoolean(Constants.REMEMBER_USER, true)
        }

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun resetError() {
        binding.tilPassword.error = null
        binding.tilEmail.error = null
    }
}