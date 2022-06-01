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

public class ConfirmDialogFragment extends DialogFragment {
    public static final String REQUEST = "requestDialog";
    public static final String MESSAGE = "message";
    public static final String KEY_BUNDLE = "result";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if(getArguments() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getArguments().getString(BaseDialogFragment.TITLE));
            LayoutInflater inflater = requireActivity().getLayoutInflater();

            View view = inflater.inflate(R.layout.confirm_dialog, null);
            ((TextView)view.findViewById(R.id.txt_message)).setText(getArguments().getString(ConfirmDialogFragment.MESSAGE));
            builder.setView(view);

            builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                Bundle result = new Bundle();
                result.putBoolean(KEY_BUNDLE, true);
                getActivity().getSupportFragmentManager().setFragmentResult(REQUEST, result);
            });

            builder.setNegativeButton(R.string.no, (dialogInterface, i) -> {
                Bundle result = new Bundle();
                result.putBoolean(KEY_BUNDLE, false);
                getActivity().getSupportFragmentManager().setFragmentResult(REQUEST, result);
            });

            return builder.create();
        }

        return null;
    }
}
