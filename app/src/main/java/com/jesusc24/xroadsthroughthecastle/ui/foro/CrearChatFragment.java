package com.jesusc24.xroadsthroughthecastle.ui.foro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.RellenarSpinner;
import com.jesusc24.xroadsthroughthecastle.data.ValidarDatos;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentCrearChatBinding;


public class CrearChatFragment extends Fragment {

    private FragmentCrearChatBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrearChatBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RellenarSpinner.information(binding.spTipo, R.array.tipo_array, this);

        binding.btCrear.setOnClickListener(v -> {
            if(validarDatos()) {
                NavHostFragment.findNavController(this).popBackStack();
            }
        });
    }

    public boolean validarDatos() {
        String nombre = binding.tilName.getEditText().getText().toString();
        String password = binding.tilPassword.getEditText().getText().toString();
        String confirm_password = binding.tilConfirmPassword.getEditText().getText().toString();

        binding.tilName.setError(ValidarDatos.validarString(nombre));
        binding.tilPassword.setError(ValidarDatos.esPasswordValida(password));
        binding.tilConfirmPassword.setError(ValidarDatos.esPasswordIgual(password, confirm_password));

        return binding.tilName.getError() == null && binding.tilPassword.getError() == null && binding.tilConfirmPassword.getError() == null;
    }
}