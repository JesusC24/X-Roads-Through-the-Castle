package com.jesusc24.xroadsthroughthecastle.ui.configuracion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager;

public class ForoFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    private PreferencesManager preferenceManager;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.foro_preferences);
        preferenceManager = new PreferencesManager(getContext());
    }


    @Override
    public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
        if(preference.getKey().equals(getString(R.string.key_order_favorito_chat))) {
            preferenceManager.putBoolean(Constants.KEY_ORDER_CHAT, (Boolean)newValue);
            return true;
        } else if(preference.getKey().equals(getString(R.string.key_ringtone_chat))) {
            preferenceManager.putString(Constants.KEY_RIGNTONE_CHAT, newValue.toString());
            return true;
        }

        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, true);
    }

    @Override
    public void onPause() {
        super.onPause();
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, false);
    }


}