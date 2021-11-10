package com.jesusc24.xroadsthroughthecastle.ui.bugs;

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
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentInformarBugBinding;

/**
 * Fragment que se utiliza para poder crear un nuevo Bug
 */
public class InformarBugFragment extends Fragment {

    FragmentInformarBugBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInformarBugBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RellenarSpinner.information(binding.spDispositivo, R.array.dispositivo_bugs_array, this);
        RellenarSpinner.information(binding.spGravedad, R.array.gravedad_bugs_array, this);
        RellenarSpinner.information(binding.spTipo, R.array.tipo_bugs_array, this);
        RellenarSpinner.information(binding.spSO, R.array.so_bugs_array, this);

        binding.btConfirmar.setOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());
    }
}