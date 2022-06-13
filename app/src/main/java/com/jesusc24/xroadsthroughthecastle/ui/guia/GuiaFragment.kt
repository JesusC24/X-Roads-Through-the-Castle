package com.jesusc24.xroadsthroughthecastle.ui.guia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants
import com.jesusc24.xroadsthroughthecastle.data.repository.UserRepository
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentGuiaBinding
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager

class GuiaFragment : Fragment() {

    private lateinit var binding: FragmentGuiaBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGuiaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.webView.loadUrl("http://sru-wiki.achisoft.net/tiki-index.php")
    }

    override fun onResume() {
        super.onResume()
        val preferenceManager = PreferencesManager(context)
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, true)
    }

    override fun onPause() {
        super.onPause()
        val preferenceManager = PreferencesManager(context)
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, true)
    }

}