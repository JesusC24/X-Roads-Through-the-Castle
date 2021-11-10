package com.jesusc24.xroadsthroughthecastle.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jesusc24.xroadsthroughthecastle.databinding.ActivityMainBinding;

/**
 * Activity que contendrá a los fragments de la aplicación
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


}