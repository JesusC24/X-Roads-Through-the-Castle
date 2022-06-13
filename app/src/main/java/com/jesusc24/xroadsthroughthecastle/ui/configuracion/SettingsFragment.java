package com.jesusc24.xroadsthroughthecastle.ui.configuracion;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.repository.UserRepository;
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    PreferencesManager preferenceManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Preferencias");
        preferenceManager = new PreferencesManager(getContext());
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onPause() {
        super.onPause();
        UserRepository.setAvailability(false, preferenceManager.getString(Constants.KEY_USER_ID));
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserRepository.setAvailability(true, preferenceManager.getString(Constants.KEY_USER_ID));
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        /*Preference preference = findPreference(key);
        //Voy a buscar si la preferencia es mi lista ringtone
        if(key.equals(getString(R.string.key_ringtone_chat))) {
            //Como he comprobado previamente que la preferencia que se ha modificado es la lista,
            //se puede hacer downcasting
            ListPreference listPreference = (ListPreference) preference;
            //Se reocge el indice seleccionado
            int index = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
            if(index >= 0) {
                preference.setSummary(listPreference.getEntries()[index]);
                //preference.setSummary(listPreference.getValue());
            } else {
                preference.setSummary(sharedPreferences.getString(key, ""));
            }
        }*/
    }
}