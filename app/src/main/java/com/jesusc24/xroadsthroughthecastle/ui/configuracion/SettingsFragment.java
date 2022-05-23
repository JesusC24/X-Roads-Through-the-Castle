package com.jesusc24.xroadsthroughthecastle.ui.configuracion;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.jesusc24.xroadsthroughthecastle.R;


public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Preferencias");
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        Preference preference = getPreferenceManager().findPreference(getString(R.string.key_help_point));
        preference.setOnPreferenceClickListener(preference1 -> {
            Toast.makeText(getActivity(), "Se ha pulsado sobre la ayuda", Toast.LENGTH_SHORT).show();
            return false; //No puede devolve43+erwrue para que no se consuma el evento y así se envie el intent
        });
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
        //TODO error al activar o destactivar las notificaciones
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
        }
    }
}