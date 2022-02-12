package com.jesusc24.xroadsthroughthecastle.ui.configuracion;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.jesusc24.xroadsthroughthecastle.R;

public class UserFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.user_preferences, rootKey);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference preference = findPreference(key);

        if(key.equals(getString(R.string.key_languages))) {
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
        }

        if(key.equals(getString(R.string.key_user_name))) {
            EditTextPreference editTextPreference = (EditTextPreference) preference;
            preference.setSummary(editTextPreference.getText());

        }
    }
}