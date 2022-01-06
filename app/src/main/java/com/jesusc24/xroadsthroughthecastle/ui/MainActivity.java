package com.jesusc24.xroadsthroughthecastle.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceFragmentCompat;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.databinding.ActivityMainBinding;

/**
 * Activity que contendrá a los fragments de la aplicación
 */
public class MainActivity extends AppCompatActivity { // implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback{

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    /*@Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, androidx.preference.Preference pref) {
        NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
        if(pref.getKey().equals(getString(R.string.key_account))) {
            navController.navigate(R.id.action_settingsFragment_to_accountFragment);
        }
        return true;
    }*/
}