package com.jesusc24.xroadsthroughthecastle.ui.singup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
        binding.btSingUp.setOnClickListener(view -> onBackPressed());
    }
}