package com.jesusc24.xroadsthroughthecastle.ui.singup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jesusc24.xroadsthroughthecastle.data.ValidarDatos;
import com.jesusc24.xroadsthroughthecastle.databinding.ActivitySingUpBinding;

/**
 * Clase que sirve que el usuario pueda registrarse antes de entrar a las secciones de la aplicación
 */
public class SingUpActivity extends AppCompatActivity {

    private ActivitySingUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySingUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*
          Se utiliza el método onBackPressed para eliminar la Activity SingUpActivity y restaurar
          la actividad anterior LoginActivity
         */
        binding.btSingUp.setOnClickListener(view -> {
            if(validarDatos()) {
                onBackPressed();
            }
        });
    }

    public boolean validarDatos() {
        String email = binding.tieEmail.getEditText().getText().toString();
        String password = binding.tiePassword.getEditText().getText().toString();
        String confirm_password = binding.tieConfirmPassword.getEditText().getText().toString();

        if(ValidarDatos.validarEmail(email) != 0) {
            binding.tieEmail.setError(this.getString(ValidarDatos.validarEmail(email)));
        } else {
            binding.tieEmail.setError(null);
        }

        if(ValidarDatos.esPasswordValida(password) != 0) {
            binding.tiePassword.setError(this.getString(ValidarDatos.esPasswordValida(password)));
        } else {
            binding.tiePassword.setError(null);
        }

        if(ValidarDatos.esPasswordIgual(password, confirm_password) != 0) {
            binding.tieConfirmPassword.setError(this.getString(ValidarDatos.esPasswordIgual(password, confirm_password)));
        } else {
            binding.tieConfirmPassword.setError(null);
        }

        return binding.tieEmail.getError() == null && binding.tiePassword.getError() == null && binding.tieConfirmPassword.getError() == null;
    }
}