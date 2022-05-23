package com.jesusc24.xroadsthroughthecastle.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants
import com.jesusc24.xroadsthroughthecastle.data.model.User
import com.jesusc24.xroadsthroughthecastle.data.repository.UserRepository
import com.jesusc24.xroadsthroughthecastle.databinding.ActivityLoginBinding
import com.jesusc24.xroadsthroughthecastle.ui.MainActivity
import com.jesusc24.xroadsthroughthecastle.ui.singUp.SignUpActivity
import com.jesusc24.xroadsthroughthecastle.utils.PreferenceManager

class LoginActivity : AppCompatActivity() {
    private val loginViewModel = LoginViewModel()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferenceManager: PreferenceManager
    companion object {
        lateinit var context : Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = applicationContext
        preferenceManager = PreferenceManager(applicationContext)

        //Inicializar Data Binding
        binding.viewmodel = loginViewModel
        //Para que aplique los cambios
        binding.executePendingBindings()
        //Para que Data Binding conozca el ciclo de vida del Fragment
        binding.lifecycleOwner = this
        binding.btSingUp.setOnClickListener {startSingUpActivity()}

        //Creamos el observador
        loginViewModel.state.observe(this) {
            when (it) {
                is State.EmailError -> setEmailError(it.message)
                is State.PasswordError -> setPasswordError(it.message)
                is State.AuthError -> setToast(it.message)
                is State.Loading -> setProgressBar(it.process)
                is State.Success -> startMainActivity()
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
            binding.includeProgressbar.progressBar.visibility = View.VISIBLE
        } else {
            binding.includeProgressbar.progressBar.visibility = View.INVISIBLE
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
    }
}