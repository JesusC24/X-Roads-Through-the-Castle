package com.jesusc24.xroadsthroughthecastle.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceFragmentCompat;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.databinding.ActivityMainBinding;
import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils;
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashSet;
import java.util.Set;

/**
 * Activity que contendrá a los fragments de la aplicación
 */
public class MainActivity extends AppCompatActivity implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private ActivityMainBinding binding;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private PreferencesManager preferenceManager;
    Toolbar toolbar;

    public final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializarVista();

        preferenceManager = new PreferencesManager(getApplicationContext());
        actualizarDatos();

        //Inicializar el controlador de navegación de la aplicación
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        //Método que configura el componente NavigationView
        //OPCIÓN 1: MOSTRAR SIEMPRE EL ICONO HAMBURGESA
        //setupNavigationView();

        //OPCIÓN 2: MOSTRAR LOS NIVELES DE FRAGMENTS MEDIANTE LA FLECHA
        Set<Integer> topLevelDestination = new HashSet<>();
        topLevelDestination.add(R.id.chatListFragment);
        topLevelDestination.add(R.id.bugListFragment);
        topLevelDestination.add(R.id.guiaFragment);
        topLevelDestination.add(R.id.settingsFragment);
        topLevelDestination.add(R.id.aboutUsFragment);

        //Configurar la barra de Acción para que funcione con NAVIGATION UI
        //Este método gestiona el elemento Click del navigationView y se mostrará el id del fragment
        //de navController, SOLO SI, el id del menú es igual al ID del fragment
        NavigationUI.setupWithNavController(binding.navigationView, navController);

        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestination)
                .setOpenableLayout(binding.drawerLayout)
                .build();

        //Con este método gestionamos la BARRA DE ACCIÓN, cuando hay varios niveles de navegación
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    /**
     * Cuando se pulsa sobre el icono de la flecha, debe ser el componente NAVIGATION UI
     * quien gestiona la navegación hacia arriba
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        actualizarDatos();
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        if(binding.drawerLayout.isOpen()) { //isDrawerOpen
            binding.drawerLayout.close(); //closeDrawer
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, androidx.preference.Preference pref) {
        NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
        if(pref.getKey().equals(getString(R.string.key_user))) {
            navController.navigate(R.id.action_settingsFragment_to_userFragment);
        } else if(pref.getKey().equals(getString(R.string.key_foro))) {
            navController.navigate(R.id.action_settingsFragment_to_foroFragment);
        } else if(pref.getKey().equals(getString(R.string.key_bug))) {
            navController.navigate(R.id.action_settingsFragment_to_bugFragment2);
        }

        return true;
    }

    public void inicializarVista() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.include.toolbar);
        setContentView(binding.getRoot());

        toolbar = binding.include.toolbar;
    }

    public void actualizarDatos() {
        String email = preferenceManager.getString(Constants.KEY_EMAIL);
        String user = preferenceManager.getString(Constants.KEY_NAME);
        String imagen = preferenceManager.getString(Constants.KEY_IMAGE);

        ((TextView)binding.navigationView.getHeaderView(0).findViewById(R.id.tvUser)).setText(user);
        ((TextView)binding.navigationView.getHeaderView(0).findViewById(R.id.tvEmail)).setText(email);

        if(imagen != null) {
            ((RoundedImageView)(binding.navigationView.getHeaderView(0).findViewById(R.id.imgUser))).setImageBitmap(CommonUtils.decodeImage(imagen));
        } else {
            ((RoundedImageView)(binding.navigationView.getHeaderView(0).findViewById(R.id.imgUser))).setImageResource(R.drawable.img_logo);
        }
    }
}