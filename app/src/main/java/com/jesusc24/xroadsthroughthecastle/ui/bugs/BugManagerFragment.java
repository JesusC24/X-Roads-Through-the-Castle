package com.jesusc24.xroadsthroughthecastle.ui.bugs;

import android.app.PendingIntent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.fragment.NavHostFragment;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.ConstBugs;
import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentInformarBugBinding;
import com.jesusc24.xroadsthroughthecastle.utils.RellenarSpinner;

//TODO cambiar el título de crear chat, y quitar el TextView
//TODO al ser uno privado, pedir contraseña para editar
//TODO poner la observación de un BUG (sin implementar), ahí tiene que estar el botón para editar
/**
 * Fragment que se utiliza para poder crear un nuevo Bug
 */
public class BugManagerFragment extends Fragment implements BugManagerContract.View {

    FragmentInformarBugBinding binding;
    BugManagerContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInformarBugBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RellenarSpinner.information(binding.spDispositivo, R.array.dispositivo_bugs_array, this);
        RellenarSpinner.information(binding.spGravedad, R.array.gravedad_bugs_array, this);
        RellenarSpinner.information(binding.spTipo, R.array.tipo_bugs_array, this);
        RellenarSpinner.information(binding.spSO, R.array.so_bugs_array, this);

        if(BugManagerFragmentArgs.fromBundle(getArguments()).getBug() != null) {
            //Se trata de editar
            //1. Cambiar el título
            getActivity().setTitle(R.string.title_edit_bug);
            //2. Rellenar los campos con los datos
            initView(BugManagerFragmentArgs.fromBundle(getArguments()).getBug());
            //3. Modifico el icono del FloatingActionButtom
            initFabEdit();
        } else {
            getActivity().setTitle(R.string.title_add_bug);
            initFabAdd();
        }

        presenter = new BugManagerPresenter(this);

    }

    /**
     * Dada un chat se inicializa la vista
     * @param bug
     */
    private void initView(Bug bug) {
        binding.tieName.setText(bug.getNombre());
        binding.tieDescripcion.setText(bug.getDescripcion());

        for(int i = 0; i< ConstBugs.Tipo.TOTAL; i++) {
            if(binding.spTipo.getItemAtPosition(i).equals(bug.getTipo())) {
                binding.spTipo.setSelection(i);
            }
        }

        for(int i = 0; i< ConstBugs.Gravedad.TOTAL; i++) {
            if(binding.spGravedad.getItemAtPosition(i).equals(bug.getGravedad())) {
                binding.spGravedad.setSelection(i);
            }
        }

        for(int i = 0; i< ConstBugs.Dispositivo.TOTAL; i++) {
            if(binding.spDispositivo.getItemAtPosition(i).equals(bug.getDispositivo())) {
                binding.spDispositivo.setSelection(i);
            }
        }

        for(int i = 0; i< ConstBugs.SO.TOTAL; i++) {
            if(binding.spSO.getItemAtPosition(i).equals(bug.getSo())) {
                binding.spSO.setSelection(i);
            }
        }

        binding.tvEstado.setText(bug.getEstado());

    }

    private void initFabEdit() {
        binding.fab.setImageResource(R.drawable.ic_action_edit);
        binding.fab.setOnClickListener(v -> presenter.edit(getBug()));
    }

    private void initFabAdd() {
        binding.fab.setOnClickListener(v -> presenter.add(getBug()));
    }

    private Bug getBug() {
        Bug bug = new Bug();

        bug.setNombre(binding.tieName.getText().toString());
        bug.setDescripcion(binding.tieDescripcion.getText().toString());
        bug.setTipo(binding.spTipo.getSelectedItem().toString());
        bug.setGravedad(binding.spGravedad.getSelectedItem().toString());
        bug.setDispositivo(binding.spDispositivo.getSelectedItem().toString());
        bug.setSo(binding.spSO.getSelectedItem().toString());
        bug.setEstado(binding.tvEstado.getText().toString());

        return bug;
    }

    @Override
    public void onAddSuccess(String message) {
        //Crear la notificación, pero ANTES se tiene que:

        //1. Crear un bundle que almacene la DEPENDENCIA
        Bundle bundle = new Bundle();
        bundle.putSerializable(Bug.TAG, getBug());


        //4. Si se utiiz el componente Navigation se utiliza el GRAFO de navegación
        //ERROR DIFICL DE DETECTAR Y ES, EL TAG DEL BUNDLE SE DEBE LLAMAR IGUAL QUE EL ARGUMENTO QUE SE HA ESTABLECIDO EN SAFEARGS
        //-> Que crea automaticamente un método según el nombre del argumento.
        //Dependency.TAG = dependency
        //Y el método de SAFE ARGS es getDependency()
        PendingIntent pendingIntent = new NavDeepLinkBuilder(getActivity())
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.bugManagerFragment)
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

    @Override
    public void onFailure(String message) {

    }
}