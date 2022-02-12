package com.jesusc24.xroadsthroughthecastle.ui.configuracion;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.jesusc24.xroadsthroughthecastle.R;

public class ForoFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.foro_preferences, rootKey);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();

        if(key.equals(getString(R.string.key_favorito_chat))) {
            SwitchPreference switchPreference = (SwitchPreference) preference;
            editor.putBoolean(getString(R.string.key_favorito_chat), switchPreference.isChecked());
        }
    }
}