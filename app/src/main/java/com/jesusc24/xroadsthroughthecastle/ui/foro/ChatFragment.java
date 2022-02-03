package com.jesusc24.xroadsthroughthecastle.ui.foro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jesusc24.xroadsthroughthecastle.databinding.FragmentChatBinding;

public class ChatFragment extends Fragment {

    FragmentChatBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentChatBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFab();
    }

    private void initFab() {
        binding.fab.setOnClickListener(v -> {
            String text = binding.tvMessage.getText().toString();


        });
    }


}