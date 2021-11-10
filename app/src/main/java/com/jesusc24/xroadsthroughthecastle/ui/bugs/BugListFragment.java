package com.jesusc24.xroadsthroughthecastle.ui.bugs;

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
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentBugListBinding;
import com.jesusc24.xroadsthroughthecastle.ui.DecorationRecyclerView;

/**
 * Fragmente que crea la lista de todos los bugs que se encuentran registrados
 */
public class BugListFragment extends Fragment {
    FragmentBugListBinding binding;
    private BugAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBugListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRvBug();

        binding.fabBug.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.action_bugListFragment_to_informarBug));
    }

    /**
     * Este método iniciliza el componente recycler view
     */
    private void initRvBug() {
        //1. Será inicilizar el adapter
        adapter = new BugAdapter();

        //2. OBLIGATORIAMENTE se debe indicar qué diseño (layout) tendrá el recyclerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        //3. Asigno el layout al recyclerView
        binding.rvBug.setLayoutManager(linearLayoutManager);

        //4. Asigno a la vista sus datos (modelo)
        binding.rvBug.setAdapter(adapter);

        //5. Asignar decoracióm a los items
        binding.rvBug.addItemDecoration(new DecorationRecyclerView(ContextCompat.getDrawable(getActivity(), R.drawable.item_decorator_chat)));
    }
}
