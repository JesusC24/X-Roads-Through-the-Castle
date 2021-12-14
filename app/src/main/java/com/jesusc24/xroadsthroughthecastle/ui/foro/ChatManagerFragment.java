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
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryListCallback;

import java.util.ArrayList;

/**
 * Clase que crea un nuevo Chat que se mostrará en ChatListFragment
 */
public class ChatManagerFragment extends Fragment implements ChatManagerContract.View, OnRepositoryListCallback {

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

        if(ValidarDatos.validarString(nombre) != 0) {
            binding.tilName.setError(this.getString(ValidarDatos.validarString(nombre)));
        } else {
            binding.tilName.setError(null);
        }

        if(ValidarDatos.esPasswordValida(password) != 0) {
            binding.tilPassword.setError(this.getString(ValidarDatos.esPasswordValida(password)));
        } else {
            binding.tilPassword.setError(null);
        }

        if(ValidarDatos.esPasswordIgual(password, confirm_password) != 0) {
            binding.tilConfirmPassword.setError(this.getString(ValidarDatos.esPasswordIgual(password, confirm_password)));
        } else {
            binding.tilConfirmPassword.setError(null);
        }

        return binding.tilName.getError() == null && binding.tilPassword.getError() == null && binding.tilConfirmPassword.getError() == null;
    }

    //region Métodos por la barra de progeso
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
    //endregion

    //region Métodos por la respuesta del repositorio
    @Override
    public void onFailure(String message) {

    }

    @Override
    public <T> void onSuccess(ArrayList<T> list) {

    }

    @Override
    public void onDeleteSuccess(String message) {

    }

    @Override
    public void onUndoSuccess(String message) {

    }
    //endregion

    //region Método dados por el presenter (CASOS DE USO)
    @Override
    public void setElementEmpty(String message) {

    }

    @Override
    public void setErrorName() {

    }

    @Override
    public void setCaracteresEspeciales(String message) {

    }

    @Override
    public void setErrorPassword() {

    }
    //endregion
}