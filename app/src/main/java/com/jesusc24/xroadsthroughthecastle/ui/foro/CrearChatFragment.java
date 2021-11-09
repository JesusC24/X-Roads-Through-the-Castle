package com.jesusc24.xroadsthroughthecastle.ui.foro;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.RellenarSpinner;
import com.jesusc24.xroadsthroughthecastle.data.ValidarDatos;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentCrearChatBinding;


import java.util.regex.Pattern;


public class CrearChatFragment extends Fragment {

    private FragmentCrearChatBinding binding;
    private TextInputLayout tilNombre, tilPassword, tilConfirmPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrearChatBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RellenarSpinner.information((Spinner) binding.spinnerTipo, R.array.tipo_array, this);

        tilNombre = binding.tilName;
        tilPassword = binding.tilPassword;
        tilConfirmPassword = binding.tilConfirmPassword;

        Button crear = binding.btCrear;
        crear.setOnClickListener(v -> {
            if(validarDatos()) {
                NavHostFragment.findNavController(this).popBackStack();
            }
        });
    }

    private boolean validarDatos() {
        String nombre = tilNombre.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();
        String confirmarPassword = tilConfirmPassword.getEditText().getText().toString();

        tilNombre.setError(ValidarDatos.validarString(nombre));
        tilPassword.setError(ValidarDatos.esPasswordValida(password));
        tilConfirmPassword.setError(ValidarDatos.esPasswordIgual(password, confirmarPassword));

        return tilNombre.getError() == null && tilPassword.getError() == null && tilConfirmPassword.getError() == null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //TODO hacer que el título de action bar sea distinto dependiende del fragment y poder poner una imagen

        // this.getActivity().getActionBar().setTitle(R.string.tvCrearChat);
        // ((MainActivity) getActivity()).getActionBar().setTitle("hola");
    }

}