package com.jesusc24.xroadsthroughthecastle.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        binding.imgbtForo.setOnClickListener(this);
        binding.imgbtNews.setOnClickListener(this);
        binding.imgbtBugs.setOnClickListener(this);
        binding.imgbtGuia.setOnClickListener(this);
        binding.imgbtAboutUs.setOnClickListener(this);
        binding.imgbtSettings.setOnClickListener(this);
        binding.imgbtUser.setOnClickListener(this);
        binding.imgbtHelp.setOnClickListener(this);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Este método es el método que maneja los eventos click de la interfaz
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case(R.id.imgbtForo): {
                showChooseChat();
                break;
            }

            case(R.id.imgbtNews): {
                showMessage(getString(R.string.dashboard_news));
                break;
            }

            case(R.id.imgbtBugs): {
                showBugs();
                break;
            }

            case(R.id.imgbtGuia): {
                showMessage(getString(R.string.dashboard_guia));
                break;
            }

            case(R.id.imgbtAboutUs): {
                showAboutUs();
                break;
            }

            case(R.id.imgbtSettings): {
                showConfiguracion();
                break;
            }

            case(R.id.imgbtUser): {
                showUser();
                break;
            }

            case(R.id.imgbtHelp): {
                showMessage(getString(R.string.dashboard_help));
                break;
            }
        }
    }

    /**
     * Mostrar el fragment AboutUs
     */
    private void showAboutUs() {
        NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_aboutUsFragment);
    }

    /**
     * Muestra el fragment que da de alta un inventario
     */
    private void showChooseChat() {
        NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_chatListFragment);
    }

    /**
     * Mostrar el fragment Bugs
     */
    private void showBugs() {
        NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_bugListFragment);
    }

    /**
     * Mostrar el fragment Configuración
     */
    private void showConfiguracion(){
        NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_configuracionFragment);
    }

    /**
     * Mostrar el fragment Usuario
     */
    private void showUser(){
        NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_modificarUserFragment);
    }


    private void showMessage(String message) {
        Toast.makeText(this.getContext(),message,Toast.LENGTH_SHORT).show();
    }

}