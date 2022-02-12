package com.jesusc24.xroadsthroughthecastle.ui.foro;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.fragment.NavHostFragment;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.XRTCApplication;
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentCrearChatBinding;
import com.jesusc24.xroadsthroughthecastle.ui.MainActivity;
import com.jesusc24.xroadsthroughthecastle.utils.RellenarSpinner;

import java.util.Random;

/**
 * Clase que crea un nuevo Chat que se mostrará en ChatListFragment
 */
public class ChatManagerFragment extends Fragment implements ChatManagerContract.View {

    private FragmentCrearChatBinding binding;
    ChatManagerContract.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCrearChatBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RellenarSpinner.information(binding.spTipo, R.array.tipo_array, this);

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

    }

    @Override
    public void onAddSuccess(String message) {
        //Crear la notificación, pero ANTES se tiene que:

        //1. Crear un bundle que almacene la DEPENDENCIA
        Bundle bundle = new Bundle();
        bundle.putSerializable(Chat.TAG, getChat());
        bundle.putSerializable(MainActivity.TAG, "");

        PendingIntent pendingIntent = new NavDeepLinkBuilder(getActivity())
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.messageFragment)
                .setArguments(bundle)
                .createPendingIntent();

        //5. Crear la notificación
        Notification.Builder builder = new Notification.Builder(getActivity(), XRTCApplication.IDCHANNEL)
                .setSmallIcon(R.drawable.ic_add_alert)
                .setContentTitle(getString(R.string.notification_title_add_dependency))
                .setContentText(message)
                .setContentIntent(pendingIntent);

        //6. Añadir la notificación al Manager
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(new Random().nextInt(1000), builder.build());

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


}