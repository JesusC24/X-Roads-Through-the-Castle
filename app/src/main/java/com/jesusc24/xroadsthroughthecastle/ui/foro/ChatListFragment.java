package com.jesusc24.xroadsthroughthecastle.ui.foro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jesusc24.xroadsthroughthecastle.R;

import com.jesusc24.xroadsthroughthecastle.databinding.FragmentChatListBinding;
import com.jesusc24.xroadsthroughthecastle.ui.DecorationRecyclerView;

public class ChatListFragment extends Fragment {
    FragmentChatListBinding binding;
    private ChatAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRvChat();

        binding.fabChat.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.action_chatListFragment_to_crearChatFragment));
    }

    /**
     * Este método iniciliza el componente recycler view
     */
    private void initRvChat() {
        //1. Será inicilizar el adapter
        adapter = new ChatAdapter();

        //2. OBLIGATORIAMENTE se debe indicar qué diseño (layout) tendrá el recyclerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        //3. Asigno el layout al recyclerView
        binding.rvChat.setLayoutManager(linearLayoutManager);

        //4. Asigno a la vista sus datos (modelo)
        binding.rvChat.setAdapter(adapter);

        //5. Asignar decoracióm a los items
        binding.rvChat.addItemDecoration(new DecorationRecyclerView(ContextCompat.getDrawable(getActivity(), R.drawable.item_decorator_chat)));
    }
}