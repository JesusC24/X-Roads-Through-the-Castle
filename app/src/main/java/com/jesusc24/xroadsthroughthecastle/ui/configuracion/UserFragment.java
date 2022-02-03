package com.jesusc24.xroadsthroughthecastle.ui.configuracion;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.jesusc24.xroadsthroughthecastle.R;

public class UserFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.user_preferences, rootKey);
    }
}