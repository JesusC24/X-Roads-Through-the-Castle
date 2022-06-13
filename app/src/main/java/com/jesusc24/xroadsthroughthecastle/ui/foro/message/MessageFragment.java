package com.jesusc24.xroadsthroughthecastle.ui.foro.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.data.model.Mensaje;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentMessageBinding;
import com.jesusc24.xroadsthroughthecastle.network.ApiClient;
import com.jesusc24.xroadsthroughthecastle.network.ApiService;
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageFragment extends Fragment implements MessageContract.View {

    FragmentMessageBinding binding;
    private Chat receiverChat;
    private List<Mensaje> mensajes = new ArrayList<>();
    private MessageAdapter adapter;
    private PreferencesManager preferenceManager;
    private MessageContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setHasOptionsMenu(true);
        presenter = new MessagePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMessageBinding.inflate(inflater);
        loadReceiverDetails();
        initRvMessages();
        listenMessages();
        initSendMesssage();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_mensajes, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_mensaje_registrar:
                presenter.activeNotification(receiverChat, preferenceManager.getString(Constants.KEY_FCM_TOKEN));
                return true;
            case R.id.menu_mensaje_quitar:
                presenter.desableNotification(receiverChat, preferenceManager.getString(Constants.KEY_FCM_TOKEN));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void loadReceiverDetails() {
        receiverChat = MessageFragmentArgs.fromBundle(getArguments()).getChat();
    }

    private void init() {
        preferenceManager = new PreferencesManager(getContext());
    }

    private void initRvMessages() {
        //1. Será inicilizar el adapter
        adapter = new MessageAdapter(new ArrayList<>(), preferenceManager.getString(Constants.KEY_USER_ID));

        //2. OBLIGATORIAMENTE se debe indicar qué diseño (layout) tendrá el recyclerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        //3. Asigno el layout al recyclerView
        binding.rvMessage.setLayoutManager(linearLayoutManager);

        //4. Asigno a la vista sus datos (modelo)
        binding.rvMessage.setAdapter(adapter);
    }

    private void initSendMesssage() {
        binding.fab.setOnClickListener(v -> presenter.sendMessage(getMessage()));
    }


    private Mensaje getMessage() {
        Mensaje mensaje = new Mensaje(
                binding.tieSendMessage.getText().toString(),
                preferenceManager.getString(Constants.KEY_USER_ID),
                receiverChat.getId().toString(),
                new Date(),
                preferenceManager.getString(Constants.KEY_IMAGE),
                preferenceManager.getString(Constants.KEY_NAME)
        );

        return mensaje;
    }

    @Override
    public void clean() {
        binding.tieSendMessage.setText(null);
    }

    public void cargarNotification(Mensaje message) {
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection(Constants.KEY_COLLECTION_FORO)
                    .document(preferenceManager.getString(Constants.KEY_FORO_ID))
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            List<String> t = (List<String>) documentSnapshot.get(Constants.KEY_NOTIFICATION_CHAT);

                            try {
                                JSONArray tokens = new JSONArray();

                                for(String token : t) {
                                    tokens.put(token);
                                }

                                JSONObject data = new JSONObject();
                                data.put(Constants.KEY_NAME, preferenceManager.getString(Constants.KEY_NAME));
                                data.put(Constants.KEY_MESSAGE, message.getTexto());
                                data.put(Constants.KEY_NAME_FORO, receiverChat.getNombre());
                                data.put(Constants.KEY_FCM_TOKEN, preferenceManager.getString(Constants.KEY_FCM_TOKEN));

                                JSONObject body = new JSONObject();
                                body.put(Constants.REMOTE_MSG_DATA, data);
                                body.put(Constants.REMOTE_MSG_REGISTRATION_IDS, tokens);

                                sendNotification(body.toString());
                            } catch (Exception exception) {

                            }
                        }

                    });

    }

    private void listenMessages() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_FORO_ID, receiverChat.getId())
                .addSnapshotListener(eventListener);
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void sendNotification(String messageBody) {
        ApiClient.getClient().create(ApiService.class).sendMessage(
                Constants.getRemoteMsgHeaders(),
                messageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful()) {
                    try{
                        if(response.body() != null) {
                            JSONObject responseJson = new JSONObject(response.body());
                            JSONArray results = responseJson.getJSONArray("results");

                            if(responseJson.getInt("failure") == 1) {
                                JSONObject error = (JSONObject) results.get(0);
                                showToast(error.getString("error"));
                                return;
                            }
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                    showToast("Notification sent successfully");

                } else {
                    showToast("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if(error != null) {
            return;
        }

        if(value != null) {
            int count = mensajes.size();
            for(DocumentChange documentChange : value.getDocumentChanges()) {
                if(documentChange.getType() == DocumentChange.Type.ADDED) {
                    Mensaje mensaje = new Mensaje(
                            documentChange.getDocument().getString(Constants.KEY_MESSAGE),
                            documentChange.getDocument().getString(Constants.KEY_SENDER_ID),
                            documentChange.getDocument().getString(Constants.KEY_FORO_ID),
                            documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP),
                            documentChange.getDocument().getString(Constants.KEY_IMAGE),
                            documentChange.getDocument().getString(Constants.KEY_NAME));

                    mensajes.add(mensaje);

                }
            }

            Collections.sort(mensajes, Comparator.comparing(Mensaje::getFecha_envio));
            if(count == 0) {
                adapter.update(mensajes);
            } else {
                adapter.newMessage(mensajes);
                binding.rvMessage.smoothScrollToPosition(mensajes.size() - 1);
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, true);
    }

    @Override
    public void onPause() {
        super.onPause();
        preferenceManager.putBoolean(Constants.KEY_AVAILABILITY, true);
    }
}