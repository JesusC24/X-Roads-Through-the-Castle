package com.jesusc24.xroadsthroughthecastle.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jesusc24.xroadsthroughthecastle.R;
import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

/**
 * Fragment que muestra el AboutUs que se usa una libería de terceros para poder hacerlo
 */
public class AboutUsFragment extends Fragment {

    private static final String TAG = "SendMesssageProject";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        AboutView view = AboutBuilder.with(getActivity())
                .setPhoto(R.mipmap.profile_picture)
                .setCover(R.mipmap.profile_cover)
                .setName("Jesús Cortés Ocaña")
                .setSubTitle(R.string.aboutUs_subTitle)
                .setBrief(R.string.aboutUs_brief)
                .setAppIcon(R.mipmap.img_icono)
                .setAppName(R.string.app_name)
                .addGitHubLink("https://github.com/JesusC24/")
                .addTwitterLink("https://twitter.com/jesuscortes2408")
                .addFiveStarsAction()
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(true)
                .build();
        return view;
    }
}