package com.jesusc24.xroadsthroughthecastle.ui.user.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.jesusc24.xroadsthroughthecastle.ui.MainActivity;
import com.jesusc24.xroadsthroughthecastle.databinding.ActivityLoginBinding;
import com.jesusc24.xroadsthroughthecastle.ui.user.singup.SingUpActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btSingUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, SingUpActivity.class);
            startActivity(intent);
        });

        binding.btSingIn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        });
    }
}