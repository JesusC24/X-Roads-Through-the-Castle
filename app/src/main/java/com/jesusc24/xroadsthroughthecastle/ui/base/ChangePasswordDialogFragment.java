package com.jesusc24.xroadsthroughthecastle.ui.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils;
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager;

public class ChangePasswordDialogFragment extends DialogFragment {
    public static final String REQUEST = "requestDialog";
    public static final String KEY_BUNDLE = "result";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ERROR = "error";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if(getArguments() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getArguments().getString(BaseDialogFragment.TITLE));
            LayoutInflater inflater = requireActivity().getLayoutInflater();

            PreferencesManager preferencesManager = new PreferencesManager(getContext());

            View view = inflater.inflate(R.layout.change_password_dialog, null);
            builder.setView(view);
            Bundle result = new Bundle();

            builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                String passwordActual = ((TextView)view.findViewById(R.id.tiePasswordActual)).getText().toString();
                String passwordNew = ((TextView)view.findViewById(R.id.tiePasswordNew)).getText().toString();
                String passwordNewConfirm = ((TextView)view.findViewById(R.id.tiePasswordNewConfim)).getText().toString();

                result.putBoolean(KEY_BUNDLE, false);

                if(passwordActual.isEmpty() || passwordNew.isEmpty() || passwordNewConfirm.isEmpty()) {
                    result.putString(KEY_ERROR, getString(R.string.errPasswordAllComplete));
                } else if(!(passwordActual.contentEquals(preferencesManager.getString(Constants.KEY_PASSWORD)))) {
                    result.putString(KEY_ERROR, getString(R.string.errPassword));
                } else if(CommonUtils.isPasswordValid(passwordNew)) {
                    result.putString(KEY_ERROR, getString(R.string.errPassword));
                } else if(!(passwordNew.contentEquals(passwordNewConfirm))) {
                    result.putString(KEY_ERROR, getString(R.string.errConfirmPassword));
                } else {
                    result.putBoolean(KEY_BUNDLE, true);
                    result.putString(KEY_PASSWORD, passwordNew);
                }
                getActivity().getSupportFragmentManager().setFragmentResult(REQUEST, result);

            });

            builder.setNegativeButton(R.string.no, (dialogInterface, i) -> {
                result.putBoolean(KEY_BUNDLE, false);
                result.putString(KEY_ERROR, "Rechazada la confirmación");
                getActivity().getSupportFragmentManager().setFragmentResult(REQUEST, result);
            });



            return builder.create();
        }

        return null;
    }


}
