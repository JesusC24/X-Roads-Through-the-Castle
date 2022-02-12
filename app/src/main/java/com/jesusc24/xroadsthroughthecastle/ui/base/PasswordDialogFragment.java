package com.jesusc24.xroadsthroughthecastle.ui.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;

public class PasswordDialogFragment extends DialogFragment {
    public static final String REQUEST = "requestDialog";
    public static final String CHAT = "chat";
    public static final String KEY_BUNDLE = "result";
    public static final String TITLE = "titulo";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if(getArguments() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getArguments().getString(BaseDialogFragment.TITLE));
            LayoutInflater inflater = requireActivity().getLayoutInflater();

            View view = inflater.inflate(R.layout.password_dialog, null);
            builder.setView(view);

            builder.setPositiveButton(R.string.entrar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String password = ((Chat)(getArguments().getSerializable(CHAT))).getPassword();
                    String intento = ((TextView)view.findViewById(R.id.tiePassword)).getText().toString();

                    if(passwordCorrect(password, intento)) {
                        Bundle result = new Bundle();
                        result.putBoolean(KEY_BUNDLE, true);
                        getActivity().getSupportFragmentManager().setFragmentResult(REQUEST, result);
                    }
                }
            });

            builder.setNegativeButton(R.string.salir, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Bundle result = new Bundle();
                    result.putBoolean(KEY_BUNDLE, false);
                    result.putSerializable(CHAT, ((Chat)(getArguments().getSerializable(CHAT))));
                    getActivity().getSupportFragmentManager().setFragmentResult(REQUEST, result);
                }
            });

            return builder.create();
        }

        return null;
    }

    private boolean passwordCorrect(String password, String intento) {
        return password.equals(intento);
    }
}
