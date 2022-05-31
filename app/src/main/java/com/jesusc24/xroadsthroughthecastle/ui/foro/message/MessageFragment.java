package com.jesusc24.xroadsthroughthecastle.ui.foro.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.data.model.Message;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentMessageBinding;
import com.jesusc24.xroadsthroughthecastle.utils.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageFragment extends Fragment implements MessageContract.View {

    FragmentMessageBinding binding;
    private Chat receiverChat;
    private List<Message> messages = new ArrayList<>();
    private MessageAdapter adapter;
    private PreferenceManager preferenceManager;
    private MessageContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MessagePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMessageBinding.inflate(inflater);
        loadReceiverDetails();
        init();
        initRvMessages();
        listenMessages();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initSendMesssage();
    }

    private void loadReceiverDetails() {
        receiverChat = MessageFragmentArgs.fromBundle(getArguments()).getChat();
        //binding.textName.setText(receiverChat.name);

    }

    private void init() {
        preferenceManager = new PreferenceManager(getContext());
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

    private Message getMessage() {
        Message message = new Message(
                binding.tieSendMessage.getText().toString(),
                preferenceManager.getString(Constants.KEY_USER_ID),
                receiverChat.getId().toString(),
                new Date()
        );

        return message;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public <T> void onSuccess(List<T> list) {

    }

    @Override
    public void onDeleteSuccess(String message) {

    }

    @Override
    public void onUndoSuccess() {

    }

    @Override
    public <T> void showData(List<T> list) {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void clean() {
        binding.tieSendMessage.setText(null);
    }

    private String getReadableDateTime(Date date) {
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date);
    }


    private void listenMessages() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_CHAT)
                .whereEqualTo(Constants.KEY_FORO_ID, receiverChat.getId())
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if(error != null) {
            return;
        }

        if(value != null) {
            int count = messages.size();
            for(DocumentChange documentChange : value.getDocumentChanges()) {
                if(documentChange.getType() == DocumentChange.Type.ADDED) {
                    Message message = new Message(
                            documentChange.getDocument().getString(Constants.KEY_MESSAGE),
                            documentChange.getDocument().getString(Constants.KEY_SENDER_ID),
                            documentChange.getDocument().getString(Constants.KEY_FORO_ID),
                            documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP));

                    messages.add(message);

                }
            }
            Collections.sort(messages, Comparator.comparing(Message::getFecha_envio));
            if(count == 0) {
                adapter.update(messages);
            } else {
                adapter.newMessage(messages);
                binding.rvMessage.smoothScrollToPosition(messages.size() - 1);
            }
        }

    };


}