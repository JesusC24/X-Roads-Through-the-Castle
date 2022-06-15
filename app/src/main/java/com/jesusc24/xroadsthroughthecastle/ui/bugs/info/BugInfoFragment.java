package com.jesusc24.xroadsthroughthecastle.ui.bugs.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentBugInfoBinding;
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager;

public class BugInfoFragment extends Fragment {

    FragmentBugInfoBinding binding;
    Bug bug;
    PreferencesManager preferencesManager;

    public static Fragment newInstance(Bundle bundle) {
        BugInfoFragment fragment = new BugInfoFragment();
        if(bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesManager = new PreferencesManager(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBugInfoBinding.inflate(inflater);
        bug = (Bug)(getArguments().getSerializable(Bug.TAG));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(bug);
    }

    private void initView(Bug bug) {
        binding.tvBugTitlo.setText(bug.getNombre());
        binding.tvBugTipoContent.setText(bug.getTipo());
        binding.tvBugDispositivoContent.setText(bug.getDispositivo());
        binding.tvBugGravedadContent.setText(bug.getGravedad());
        binding.tvBugSOContent.setText(bug.getSo());
    }

    @Override
    public void onResume() {
        super.onResume();
        preferencesManager.putBoolean(Constants.KEY_AVAILABILITY, true);
    }

    @Override
    public void onPause() {
        super.onPause();
        preferencesManager.putBoolean(Constants.KEY_AVAILABILITY, false);
    }
}