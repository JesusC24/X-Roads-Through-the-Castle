package com.jesusc24.xroadsthroughthecastle.ui.configuracion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager;

public class UserFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener, PreferenceManager.OnPreferenceTreeClickListener, OnRepositoryCallback {

    private PreferencesManager preferenceManager;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.user_preferences);
        preferenceManager = new PreferencesManager(getContext());

        Preference preference = findPreference(getString(R.string.KEY_NAME));
        preference.setOnPreferenceChangeListener(this);
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
}