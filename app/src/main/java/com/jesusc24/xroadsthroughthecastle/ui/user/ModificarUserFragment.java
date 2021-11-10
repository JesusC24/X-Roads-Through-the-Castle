package com.jesusc24.xroadsthroughthecastle.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jesusc24.xroadsthroughthecastle.databinding.FragmentModificarUserBinding;

/**
 * Fragment que se usa para poder modificar un usuario ya existente
 */
public class ModificarUserFragment extends Fragment {
    FragmentModificarUserBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentModificarUserBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.tilPassword.setVisibility(View.INVISIBLE);
        binding.tilConfirmPassword.setVisibility(View.INVISIBLE);

        binding.btCambiarPassword.setOnClickListener(v -> {
            binding.tilPassword.setVisibility(View.VISIBLE);
            binding.tilConfirmPassword.setVisibility(View.VISIBLE);
        });

        binding.btConfirmarCambios.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());
    }
}