package com.jesusc24.xroadsthroughthecastle.ui.foro;

import android.app.PendingIntent;
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
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentCrearChatBinding;
import com.jesusc24.xroadsthroughthecastle.utils.RellenarSpinner;

//TODO poner la observación de un CHAT (sin implementar), ahí tiene que estar el botón para editar
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
            getActivity().setTitle(R.string.title_edit_chat);
            //2. Rellenar los campos con los datos
            initView(ChatManagerFragmentArgs.fromBundle(getArguments()).getChat());
            //3. Modifico el icono del FloatingActionButtom
            initFabEdit();
        } else {
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

        //2. Crear un intent (ESTO ES EN EL CASO DE TRABAJAR CON ACTIVIDADES)
        //Intent intent = new Intent(getActivity(), SplashActivity.class);
        //intent.putExtras(bundle);
        //3. Crear PendingIntent que contiene el Intent
        //PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), new Random().nextInt(1000), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //4. Si se utiiz el componente Navigation se utiliza el GRAFO de navegación
        //ERROR DIFICL DE DETECTAR Y ES, EL TAG DEL BUNDLE SE DEBE LLAMAR IGUAL QUE EL ARGUMENTO QUE SE HA ESTABLECIDO EN SAFEARGS
        //-> Que crea automaticamente un método según el nombre del argumento.
        //Dependency.TAG = dependency
        //Y el método de SAFE ARGS es getDependency()
        PendingIntent pendingIntent = new NavDeepLinkBuilder(getActivity())
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.chatManagerFragment)
                .setArguments(bundle)
                .createPendingIntent();

        /*//5. Crear la notificación
        Notification.Builder builder = new Notification.Builder(getActivity(), InventoryApplication.IDCHANNEL)
                .setSmallIcon(R.drawable.ic_add_alert)
                .setContentTitle(getString(R.string.notification_title_add_dependency))
                .setContentText(message)
                .setContentIntent(pendingIntent);

        //6. Añadir la notificación al Manager
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(new Random().nextInt(1000), builder.build());*/

        NavHostFragment.findNavController(this).popBackStack();
    }

    @Override
    public void onEditSucess(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        NavHostFragment.findNavController(this).popBackStack();
    }


    //region Casos de usos
    @Override
    public void setNameEmptyError() {
        binding.tilName.setError(getString(R.string.errNameEmpty));
    }

    @Override
    public void setPasswordError() {
        binding.tilPassword.setError(getString(R.string.errPassword));
    }

    @Override
    public void setConfirmPasswordError() {
        binding.tilConfirmPassword.setError(getString(R.string.errPassword));
    }

    @Override
    public void setPasswordDontMatch() {
        binding.tilConfirmPassword.setError(getString(R.string.errConfirmPassword));
    }
    //endregion


}