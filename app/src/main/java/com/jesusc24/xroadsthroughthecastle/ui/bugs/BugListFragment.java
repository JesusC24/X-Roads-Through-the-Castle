package com.jesusc24.xroadsthroughthecastle.ui.bugs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentBugListBinding;
import com.jesusc24.xroadsthroughthecastle.ui.DecorationRecyclerView;
import com.jesusc24.xroadsthroughthecastle.ui.base.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;

//TODO implementar la búsqueda
/**
 * Fragmente que crea la lista de todos los bugs que se encuentran registrados
 */
public class BugListFragment extends Fragment implements BugListContract.View, BugAdapter.OnManageBugList {
    FragmentBugListBinding binding;
    private BugAdapter adapter;
    private BugListContract.Presenter presenter;
    private Bug deleted;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new BugListPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBugListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.title_list_bug);
        initRvBug();
        initFavBug();
    }

    public void initFavBug() {
        binding.fabBug.setOnClickListener(v -> {
            BugListFragmentDirections.ActionBugListFragmentToInformarBug action = BugListFragmentDirections.actionBugListFragmentToInformarBug(null);
            NavHostFragment.findNavController(this).navigate(action);
        });
    }

    /**
     * Este método iniciliza el componente recycler view
     */
    private void initRvBug() {
        //1. Será inicilizar el adapter
        adapter = new BugAdapter(new ArrayList<>(), this);

        //2. OBLIGATORIAMENTE se debe indicar qué diseño (layout) tendrá el recyclerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        //3. Asigno el layout al recyclerView
        binding.rvBug.setLayoutManager(linearLayoutManager);

        //4. Asigno a la vista sus datos (modelo)
        binding.rvBug.setAdapter(adapter);

        //5. Asignar decoracióm a los items
        binding.rvBug.addItemDecoration(new DecorationRecyclerView(ContextCompat.getDrawable(getActivity(), R.drawable.item_decorator_chat)));
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.load();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        if(sharedPreferences.getBoolean(getString(R.string.key_resultado_bug), false)) {
            presenter.orderByEstado();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_bug_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_bug_ordenar:
                presenter.order();
                return true;

            case R.id.menu_bug_ordenar_resultsado:
                presenter.orderByEstado();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void showProgress() {
        binding.includeProgressbar.llProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.includeProgressbar.llProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public <T> void onSuccess(List<T> list) {

    }

    @Override
    public void onDeleteSuccess(String message) {
        Snackbar.make(getView(), message, BaseTransientBottomBar.LENGTH_SHORT).setAction("UNDO", view -> {
            presenter.undo(deleted);
        });

        adapter.delete(deleted);

        if(adapter.isEmpty()) {
            showNoData();
        }
    }

    @Override
    public void onUndoSuccess() {
        adapter.undo(deleted);
    }

    @Override
    public <T> void showData(List<T> list) {
        if(binding.llNoDataBug.getVisibility()==View.VISIBLE) {
            binding.llNoDataBug.setVisibility(View.GONE);
        }
        adapter.update((List<Bug>) list);
    }

    @Override
    public void showNoData() {
        if(binding.llNoDataBug.getVisibility()==View.GONE) {
            binding.llNoDataBug.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showDataOrder() {
        adapter.order();
    }

    @Override
    public void showDataInverseOrder() {
        adapter.inverseOrder();
    }

    @Override
    public void showByEstadoOrder() {
        adapter.orderByEstado();
    }

    @Override
    public void onShowBug(Bug bug) {
        BugListFragmentDirections.ActionBugListFragmentToBugFragment action = BugListFragmentDirections.actionBugListFragmentToBugFragment(bug);
        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void onDeleteBug(Bug bug) {
        Bundle bundle = new Bundle();
        bundle.putString(BaseDialogFragment.TITLE, "Elimnar bug");
        bundle.putString(BaseDialogFragment.MESSAGE, String.format("¿Seguro que desea borrar el bug %1$s?", bug.getNombre()));
        NavHostFragment.findNavController(this).navigate(R.id.action_bugListFragment_to_baseDialogFragment, bundle);

        getActivity().getSupportFragmentManager().setFragmentResultListener(BaseDialogFragment.REQUEST, this, (requestKey, result) -> {
            if(result.getBoolean(BaseDialogFragment.KEY_BUNDLE)) {
                deleted = bug;
                presenter.delete(bug);
            }
        });
    }
}
