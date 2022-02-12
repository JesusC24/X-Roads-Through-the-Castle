package com.jesusc24.xroadsthroughthecastle.ui.foro.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentMessageBinding;
import com.jesusc24.xroadsthroughthecastle.ui.MainActivity;

public class MessageFragment extends Fragment {

    FragmentMessageBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMessageBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_messages);
        } catch (Exception e) {

        }

        initFab();
    }

    private void initFab() {
        binding.fab.setOnClickListener(v -> {
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_messages_edit:
                edit();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void edit() {
        MessageFragmentDirections.ActionChatFragmentToChatManagerFragment action = MessageFragmentDirections.actionChatFragmentToChatManagerFragment(MessageFragmentArgs.fromBundle(getArguments()).getChat());
        NavHostFragment.findNavController(this).navigate(action);
    }
}