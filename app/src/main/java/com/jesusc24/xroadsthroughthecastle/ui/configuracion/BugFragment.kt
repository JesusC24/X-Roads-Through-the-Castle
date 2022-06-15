package com.jesusc24.xroadsthroughthecastle.ui.configuracion

import androidx.preference.PreferenceFragmentCompat
import android.os.Bundle
import androidx.preference.Preference
import com.jesusc24.xroadsthroughthecastle.R
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager

class BugFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {
    private lateinit var preferenceManager: PreferencesManager
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.bug_preferences)
        preferenceManager = PreferencesManager(context)
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        if (preference.key == getString(R.string.key_order_bug)) {
            preferenceManager.putBoolean(Constants.KEY_ORDER_BUG, newValue as Boolean)
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