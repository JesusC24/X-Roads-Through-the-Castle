package com.jesusc24.xroadsthroughthecastle.ui.configuracion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jesusc24.xroadsthroughthecastle.databinding.FragmentConfiguracionBinding;

/**
 * Muestra el layout del frament configuración
 */
public class ConfiguracionFragment extends Fragment {

    private FragmentConfiguracionBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfiguracionBinding.inflate(inflater);
        return binding.getRoot();
    }
}