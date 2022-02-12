package com.jesusc24.xroadsthroughthecastle.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.model.User;
import com.jesusc24.xroadsthroughthecastle.databinding.ActivityLoginBinding;
import com.jesusc24.xroadsthroughthecastle.ui.MainActivity;
import com.jesusc24.xroadsthroughthecastle.ui.singUp.SignUpActivity;
import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private ActivityLoginBinding binding;
    private LoginContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btSingUp.setOnClickListener(view -> startSingUpActivity());

        binding.btSingIn.setOnClickListener(view -> presenter.validateCredetials(new User(binding.tieEmail.getText().toString(), binding.tiePassword.getText().toString())));

        binding.btGoogle.setOnClickListener(view -> {
            presenter.loginWithGoogle(getString(R.string.defaul_web_client_id), this);
        });

        binding.btFacebook.setOnClickListener(v -> {
            Toast.makeText(this, getString(R.string.prepareFacebook), Toast.LENGTH_SHORT).show();
        });

        binding.tieEmail.addTextChangedListener(new LoginTextWatcher(binding.tieEmail));
        binding.tiePassword.addTextChangedListener(new LoginTextWatcher(binding.tiePassword));

        presenter = new LoginPresenter(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.resultGoogle(requestCode, resultCode, data);
    }

    void startSingUpActivity() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy(); //Se evitaría un futuro memmory leaks
        presenter = null;
        //Se quita como subscriptor de Event Bus
        EventBus.getDefault().unregister(this);
    }


    //region Métodos del contrato View-Presente

    /**
     * Activa el error en el componente en TextInputLayout y mostrar el texto oportuno
     */
    @Override
    public void setEmailEmptyError() {
        binding.tilEmail.setError(getString(R.string.errEmailEmpty));
    }

    @Override
    public void setPasswordEmptyError() {
        binding.tilPassword.setError(getString(R.string.errPasswordEmpty));
    }

    @Override
    public void setPasswordError() {
        binding.tilPassword.setError(getString(R.string.errPassword));
    }

    @Override
    public void setEmailError() {
        binding.tilEmail.setError(getString(R.string.errEmail));
    }

    //Secuencia normal: el usuario existe en la base de datos, usuario-contraseña correctos.
    @Override
    public void onSuccess(String message, User user) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();

        if(binding.chxRemember.isChecked()) {
            editor.putBoolean(User.REMBEBER, true);
            editor.apply();
        }

        startMainActivity();
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        binding.includeProgressbar.llProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.includeProgressbar.llProgressBar.setVisibility(View.INVISIBLE);
    }
    //endregion

    // region Clase interna que controla cada vez que el usuario introduce un carácter en un
    // Editable si cumple o no las reglas de validación.

    class LoginTextWatcher implements TextWatcher {
        private View view;

        LoginTextWatcher(View view) {
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
                case R.id.tieEmail:
                    validateEmail(((EditText) view).getText().toString());
                    break;
                case R.id.tiePassword:
                    validatePassword(((EditText) view).getText().toString());
                    break;
            }
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
        } else if (!CommonUtils.isEmailValid(email)) {
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

}