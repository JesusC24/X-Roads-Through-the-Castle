package com.jesusc24.xroadsthroughthecastle.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jesusc24.xroadsthroughthecastle.R
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager
import com.vansuita.materialabout.builder.AboutBuilder

class AboutUsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return AboutBuilder.with(activity!!)
            .setPhoto(R.mipmap.profile_picture)
            .setCover(R.mipmap.profile_cover)
            .setName(R.string.my_name)
            .setSubTitle(R.string.aboutUs_subTitle)
            .setBrief(R.string.aboutUs_brief)
            .setAppIcon(R.mipmap.img_icono)
            .setAppName(R.string.app_name)
            .addGitHubLink("JesusC24")
            .addTwitterLink("jesuscortes2408")
            .addLinkedInLink("jesús-cortés-ocaña-359631221")
            .addFiveStarsAction()
            .setVersionNameAsAppSubTitle()
            .addShareAction(R.string.app_name)
            .setWrapScrollView(true)
            .setLinksAnimated(true)
            .setShowAsCard(true)
            .build()
    }

    override fun onResume() {
        super.onResume()
        val preferenceManager = PreferencesManager(context)
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, true)

    }

    override fun onPause() {
        super.onPause()
        val preferenceManager = PreferencesManager(context)
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, false)
    }
}