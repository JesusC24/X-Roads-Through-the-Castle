package com.jesusc24.xroadsthroughthecastle.ui.configuracion

import androidx.preference.PreferenceFragmentCompat
import android.os.Bundle
import androidx.preference.Preference
import com.jesusc24.xroadsthroughthecastle.R
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager

class ForoFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
    private lateinit var preferenceManager: PreferencesManager
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.foro_preferences)
        preferenceManager = PreferencesManager(context)
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        if (preference.key == getString(R.string.key_order_favorito_chat)) {
            preferenceManager.putBoolean(Constants.KEY_ORDER_CHAT, newValue as Boolean)
            return true
        } else if (preference.key == getString(R.string.key_ringtone_chat)) {
            preferenceManager.putString(Constants.KEY_RIGNTONE_CHAT, newValue.toString())
            return true
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, true)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, false)
    }
}