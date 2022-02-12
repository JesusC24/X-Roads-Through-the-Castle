package com.jesusc24.xroadsthroughthecastle.ui.singUp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.model.User;
import com.jesusc24.xroadsthroughthecastle.databinding.ActivitySingUpBinding;
import com.jesusc24.xroadsthroughthecastle.ui.base.Event;
import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class SignUpActivity extends AppCompatActivity implements SignUpContract.View {

    private ActivitySingUpBinding binding;
    SignUpContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySingUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new SignUpPresenter(this);

        binding.tieUser.addTextChangedListener(new SignUpTextWatcher(binding.tieUser));
        binding.tieEmail.addTextChangedListener(new SignUpTextWatcher(binding.tieEmail));
        binding.tiePassword.addTextChangedListener(new SignUpTextWatcher(binding.tiePassword));

        binding.btSingUp.setOnClickListener(v -> presenter.validateSingUp(new User(binding.tieEmail.getText().toString(), binding.tiePassword.getText().toString(), binding.tieUser.getText().toString(), binding.tieConfirmPassword.getText().toString())));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        presenter.onDestroy();
        presenter = null;
    }

    @Override
    public void setUserEmptyError() {
        binding.tilUser.setError(getString(R.string.errUserEmpty));
    }

    @Override
    public void setEmailEmptyError() {
        binding.tilEmail.setError(getString(R.string.errEmailEmpty));
    }

    @Override
    public void setPasswordEmptyError() {
        binding.tilPassword.setError(getString(R.string.errPasswordEmpty));
    }

    @Override
    public void setConfirmPasswordEmptyError() {
        binding.tilConfirmPassword.setError(getString(R.string.errConfirmPasswordEmpty));
    }

    @Override
    public void setPasswordError() {
        binding.tilPassword.setError(getString(R.string.errPassword));
    }

    @Override
    public void setEmailError() {
        binding.tilEmail.setError(getString(R.string.errEmail));
    }

    @Override
    public void setPasswordDontMatch() {
        binding.tilConfirmPassword.setError(getString(R.string.errConfirmPassword));
    }

    @Override
    public void onSuccess(String message, User user) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();

        editor.putString(getString(R.string.key_user_name), user.getUser());
        editor.putString(User.EMAIL, user.getEmail());
        editor.apply();

        finish();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // region Clase interna que controla cada vez que el usuario introduce un carácter en un
    // Editable si cumple o no las reglas de validación.

    class SignUpTextWatcher implements TextWatcher {
        private View view;

        SignUpTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.tieUser:
                    validateUser(((EditText) view).getText().toString());
                    binding.tilUser.setError(null);
                case R.id.tieEmail:
                    validateEmail(((EditText) view).getText().toString());
                    break;
                case R.id.tiePassword:
                    validatePassword(((EditText) view).getText().toString());
                    break;
            }
        }
    }

    private void validateUser(String user) {
        if (TextUtils.isEmpty(user)) {
            binding.tilUser.setError(getString(R.string.errUserEmpty));
        } else {
            // Desaparece el error.
            binding.tilUser.setError(null);
        }
    }

    /**
     * Método que valida el email.
     * 1. No puede ser vacío.
     * 2. Vamos a utilizar el patrón por defcto de email para comprobar que tiene el formato
     * correcto.
     */
    private void validateEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            binding.tilEmail.setError(getString(R.string.errEmailEmpty));
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.setError(getString(R.string.errEmail));
        } else {
            // Desaparece el error.
            binding.tilEmail.setError(null);
        }
    }

    /**
     * Método que valida la contraseña mediante el método ya creado de la clase CommonUtils
     */
    private void validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            binding.tilPassword.setError(getString(R.string.errPasswordEmpty));
        } else if (!CommonUtils.isPasswordValid(password)) {
            binding.tilPassword.setError(getString(R.string.errPassword));
        } else {
            binding.tilPassword.setError(null);
        }
    }
    // endregion

    //region Método que se ejecuta cuando hay un evento del Repositorio
    @Subscribe
    public void onEvent(Event event) {
        Toast.makeText(this, event.getMessage(),Toast.LENGTH_SHORT).show();
    }
    //endregion
}