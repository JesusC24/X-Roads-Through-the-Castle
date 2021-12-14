package com.jesusc24.xroadsthroughthecastle.ui.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BaseDialogFragment extends DialogFragment {
    public static final String REQUEST = "requestDialog";
    public static final String KEY_BUNDLE = "result";
    public static final String TITLE = "titulo";
    public static final String MESSAGE = "message";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if(getArguments() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getArguments().getString(BaseDialogFragment.TITLE));
            builder.setMessage(getArguments().getString(BaseDialogFragment.MESSAGE));
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Bundle result = new Bundle();
                    result.putBoolean(KEY_BUNDLE, true);
                    getActivity().getSupportFragmentManager().setFragmentResult(REQUEST, result);
                }
            });

            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Bundle result = new Bundle();
                    result.putBoolean(KEY_BUNDLE, false);
                    getActivity().getSupportFragmentManager().setFragmentResult(REQUEST, result);
                }
            });

            return builder.create();
        }

        return null;
    }
}
