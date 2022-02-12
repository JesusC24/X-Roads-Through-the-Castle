package com.jesusc24.xroadsthroughthecastle.ui.bugs.comentarios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.jesusc24.xroadsthroughthecastle.databinding.FragmentBugComentariosBinding;

public class BugComentariosFragment extends Fragment {

    FragmentBugComentariosBinding binding;
    public static final String TAG = "BugComentariosFragment";

    public static Fragment newInstance(Bundle bundle) {
        BugComentariosFragment fragment = new BugComentariosFragment();
        if(bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBugComentariosBinding.inflate(inflater);
        return binding.getRoot();
    }
}