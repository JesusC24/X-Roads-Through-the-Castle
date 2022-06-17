package com.jesusc24.xroadsthroughthecastle.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentCrearChatBinding;
import com.jesusc24.xroadsthroughthecastle.ui.MainActivity;
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager;
import com.jesusc24.xroadsthroughthecastle.utils.RellenarSpinner;

/**
 * Clase que crea un nuevo Chat que se mostrará en ChatListFragment
 */
public class ChatManagerFragment extends Fragment implements ChatManagerContract.View {

    private FragmentCrearChatBinding binding;
    ChatManagerContract.Presenter presenter;
    PreferencesManager preferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrearChatBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RellenarSpinner.information(binding.spTipo, R.array.tipo_array, this);
        preferenceManager = new PreferencesManager(getContext());

        if(ChatManagerFragmentArgs.fromBundle(getArguments()).getChat() != null) {
            //Se trata de editar
            //1. Cambiar el título
            ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_edit_chat);

            //2. Rellenar los campos con los datos
            initView(ChatManagerFragmentArgs.fromBundle(getArguments()).getChat());
            //3. Modifico el icono del FloatingActionButtom
            initFabEdit();
        } else {
            ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_add_chat);
            initFabAdd();
        }

        binding.spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                if(pos == 0) { //La posición 0 es PÚBLICO, la posición 1 es PRIVADO
                    binding.tilPassword.setVisibility(View.GONE);
                    binding.tilConfirmPassword.setVisibility(View.GONE);
                } else {
                    binding.tilPassword.setVisibility(View.VISIBLE);
                    binding.tilConfirmPassword.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        presenter = new ChatManagerPresenter(this);
    }

    /**
     * Dada un chat se inicializa la vista
     * @param chat
     */
    private void initView(Chat chat) {
        binding.tieName.setText(chat.getNombre());
        binding.tieDescripcion.setText(chat.getDescripcion());

        if(chat.getTipo().equals(Chat.PUBLICO)) {
            binding.spTipo.setSelection(0);
        } else {
            binding.spTipo.setSelection(1);
            binding.tiePassword.setText(chat.getPassword());
            binding.tieConfirmPassword.setText(chat.getConfirmPassword());
        }
    }

    private void initFabEdit() {
        binding.fab.setImageResource(R.drawable.ic_action_edit);
        binding.tieName.setEnabled(false);
        binding.fab.setOnClickListener(v -> presenter.edit(getChat()));
    }

    private void initFabAdd() {
        binding.fab.setOnClickListener(v -> presenter.add(getChat()));
    }

    private Chat getChat() {
        Chat chat = new Chat();

        resetError();

        chat.setNombre(binding.tieName.getText().toString());

        if(binding.spTipo.getSelectedItemPosition() == 0) {
            chat.setTipo(Chat.PUBLICO);
        } else {
            chat.setTipo(Chat.PRIVADO);
        }

        if(chat.getTipo().equals(Chat.PRIVADO)) {
            chat.setPassword(binding.tiePassword.getText().toString());
            chat.setConfirmPassword(binding.tieConfirmPassword.getText().toString());
        }

        chat.setDescripcion(binding.tieDescripcion.getText().toString());

        if(ChatManagerFragmentArgs.fromBundle(getArguments()).getChat()!=null){
            chat.setId(ChatManagerFragmentArgs.fromBundle(getArguments()).getChat().getId());
        }

        chat.setFavorito(false);

        chat.setIdUser(preferenceManager.getString(Constants.KEY_USER_ID));

        return chat;
    }

    private void resetError() {
        binding.tilName.setError(null);
        binding.tilPassword.setError(null);
        binding.tilConfirmPassword.setError(null);
    }

    //region Métodos por la respuesta del repositorio
    @Override
    public void onFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddSuccess(String message) {
        Toast.makeText(getContext(), getString(R.string.addChat) + " " + message + " " + getString(R.string.exit), Toast.LENGTH_SHORT).show();
        NavHostFragment.findNavController(this).popBackStack();
    }

    @Override
    public void onEditSucess(String message) {
        Toast.makeText(getContext(), getString(R.string.editSuccessChat) + message + getString(R.string.exit), Toast.LENGTH_SHORT).show();
        NavHostFragment.findNavController(this).popBackStack();
    }

    //region Casos de usos
    @Override
    public void setNameEmptyError() {
        binding.tilName.setError(getString(R.string.errNameEmpty));
    }

    @Override
    public void setPasswordError() {
        binding.tilPassword.setError(getString(R.string.errPasswordBug));
    }

    @Override
    public void setConfirmPasswordError() {
        binding.tilConfirmPassword.setError(getString(R.string.errPasswordBug));
    }

    @Override
    public void setPasswordDontMatch() {
        binding.tilConfirmPassword.setError(getString(R.string.errConfirmPassword));
    }
    //endregion

    @Override
    public void onResume() {
        super.onResume();
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, true);
    }

    @Override
    public void onPause() {
        super.onPause();
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, false);
    }
}