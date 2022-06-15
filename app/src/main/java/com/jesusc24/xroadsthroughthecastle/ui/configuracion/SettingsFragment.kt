package com.jesusc24.xroadsthroughthecastle.ui.configuracion

import androidx.preference.PreferenceFragmentCompat

import android.os.Bundle
import com.jesusc24.xroadsthroughthecastle.R

import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var preferenceManager: PreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity!!.title = "Preferencias"
        preferenceManager = PreferencesManager(context)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, true)
    }

}