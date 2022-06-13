package com.jesusc24.xroadsthroughthecastle.ui.configuracion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.repository.UserRepository;
import com.jesusc24.xroadsthroughthecastle.ui.base.ChangePasswordDialogFragment;
import com.jesusc24.xroadsthroughthecastle.ui.base.ConfirmDialogFragment;
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryCallback;
import com.jesusc24.xroadsthroughthecastle.ui.base.PasswordDialogFragment;
import com.jesusc24.xroadsthroughthecastle.ui.login.LoginActivity;
import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils;
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager;

import java.io.IOException;

public class UserFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener, PreferenceManager.OnPreferenceTreeClickListener, OnRepositoryCallback {

    private static final int PICK_IMAGE = 100;
    private static final int RESULT_OK = -1;
    private PreferencesManager preferenceManager;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.user_preferences);
        preferenceManager = new PreferencesManager(getContext());
    }

    @Override
    public boolean onPreferenceTreeClick(@NonNull Preference preference) {
        if(preference.getKey().equals(getString(R.string.key_user_closed_session))) {
            Bundle bundle = new Bundle();
            bundle.putString(ConfirmDialogFragment.MESSAGE, getString(R.string.confirmCloseSession));
                NavHostFragment.findNavController(this).navigate(R.id.action_userFragment_to_confirmDialogFragment, bundle);

                getActivity().getSupportFragmentManager().setFragmentResultListener(ConfirmDialogFragment.REQUEST, this, (requestKey, result) -> {
                    if(result.getBoolean(PasswordDialogFragment.KEY_BUNDLE)) {
                        preferenceManager.putBoolean(Constants.REMEMBER_USER, false);
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        getActivity().finish();
                    }

                });

            return true;
        } else if(preference.getKey().equals(getString(R.string.key_user_delete))){
            Bundle bundle = new Bundle();
            bundle.putString(ConfirmDialogFragment.MESSAGE, getString(R.string.confirmDeleteAccount));
            NavHostFragment.findNavController(this).navigate(R.id.action_userFragment_to_confirmDialogFragment, bundle);

            getActivity().getSupportFragmentManager().setFragmentResultListener(ConfirmDialogFragment.REQUEST, this, (requestKey, result) -> {
                if(result.getBoolean(PasswordDialogFragment.KEY_BUNDLE)) {
                    preferenceManager.putBoolean(Constants.REMEMBER_USER, false);
                    UserRepository.getInstance(this).deleteUser(preferenceManager.getString(Constants.KEY_USER_ID));
                }
            });

            return true;

        } else if(preference.getKey().equals(getString(R.string.key_user_change_password))) {
            Bundle bundle = new Bundle();
            bundle.putString(PasswordDialogFragment.TITLE, getString(R.string.changePassword));
            NavHostFragment.findNavController(this).navigate(R.id.action_userFragment_to_changePasswordDialogFragment, bundle);

            getActivity().getSupportFragmentManager().setFragmentResultListener(ConfirmDialogFragment.REQUEST, this, (requestKey, result) -> {
                if(result.getBoolean(ChangePasswordDialogFragment.KEY_BUNDLE)) {
                    String password = result.getString(ChangePasswordDialogFragment.KEY_PASSWORD);
                    UserRepository.getInstance(this).updatePassword(password, preferenceManager.getString(Constants.KEY_USER_ID));
                } else {
                    Toast.makeText(getContext(), result.getString(ChangePasswordDialogFragment.KEY_ERROR), Toast.LENGTH_SHORT).show();
                }
            });
        } else if(preference.getKey().equals(getString(R.string.key_user_change_image))) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, PICK_IMAGE);
        }
        return false;
    }

    @Override
    public void onSuccess() {
        getActivity().finish();
    }

    @Override
    public void onFailure(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
        if(preference.getKey().equals(getString(R.string.KEY_NAME))) {
            preferenceManager.putString(Constants.KEY_NAME, newValue.toString());
            UserRepository.getInstance(this).updateNameUser(newValue.toString(), preferenceManager.getString(Constants.KEY_USER_ID));
        }

        if(preference.getKey().equals(getString(R.string.key_user_closed_session))) {
            preferenceManager.putBoolean(Constants.REMEMBER_USER, false);
            startActivity(new Intent(getContext(), LoginActivity.class));
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String encodeImage = CommonUtils.encodeImage(bitmap);
            preferenceManager.putString(Constants.KEY_IMAGE, encodeImage);
            UserRepository.getInstance(this).changeImage(encodeImage, preferenceManager.getString(Constants.KEY_USER_ID));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        UserRepository.setAvailability(true, preferenceManager.getString(Constants.KEY_USER_ID));
    }

    @Override
    public void onPause() {
        super.onPause();
        UserRepository.setAvailability(false, preferenceManager.getString(Constants.KEY_USER_ID));
    }
}