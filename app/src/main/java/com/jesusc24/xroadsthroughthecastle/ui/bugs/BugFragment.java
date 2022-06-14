package com.jesusc24.xroadsthroughthecastle.ui.bugs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.ConstBugs;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentBugBinding;
import com.jesusc24.xroadsthroughthecastle.ui.MainActivity;
import com.jesusc24.xroadsthroughthecastle.ui.bugs.especificaciones.BugDescriptionFragment;
import com.jesusc24.xroadsthroughthecastle.ui.bugs.info.BugInfoFragment;
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager;

public class BugFragment extends Fragment {

    FragmentBugBinding binding;
    Bug bug;
    PreferencesManager preferenceManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        preferenceManager = new PreferencesManager(getContext());

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.edit_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBugBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bug = BugManagerFragmentArgs.fromBundle(getArguments()).getBug();

        try {
            ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_bug);
        } catch (Exception e) {

        }

        initNavigation();
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Bug.TAG, bug);
        loadFragment(BugInfoFragment.newInstance(bundle));
    }

    private void initNavigation() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Bug.TAG, bug);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_bug_info:
                    loadFragment(BugInfoFragment.newInstance(bundle));
                    break;
                case R.id.action_bug_description:
                    loadFragment(BugDescriptionFragment.newInstance(bundle));
                    break;
            }

            return true;
        });
    }

    /**
     * Este método carga un fragment "child" dentro de otro fragment. Para ello se utiliza el método gwtChildManager
     * para gestionar fragments anidados.
     * @param newInstance
     */
    private void loadFragment(Fragment newInstance) {
        if(newInstance != null) {
            getChildFragmentManager().beginTransaction().replace(R.id.productContent, newInstance).commit();
        }
    }

    private void edit() {
        if(bug.getEstado().contains(ConstBugs.Estado.DENEGADO) || bug.getEstado().contains(ConstBugs.Estado.ARREGLADO)) {
            Toast.makeText(getContext(), getString(R.string.errEditBug), Toast.LENGTH_SHORT).show();
        } else {
            BugFragmentDirections.ActionBugFragmentToBugManagerFragment action = BugFragmentDirections.actionBugFragmentToBugManagerFragment(bug);
            NavHostFragment.findNavController(this).navigate(action);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, true);
    }

    @Override
    public void onPause() {
        super.onPause();
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, true);
    }
}