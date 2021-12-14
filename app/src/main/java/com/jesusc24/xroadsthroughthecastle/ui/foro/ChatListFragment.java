package com.jesusc24.xroadsthroughthecastle.ui.foro;

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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.jesusc24.xroadsthroughthecastle.R;

import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentChatListBinding;
import com.jesusc24.xroadsthroughthecastle.ui.DecorationRecyclerView;
import com.jesusc24.xroadsthroughthecastle.ui.base.BaseDialogFragment;

import java.util.ArrayList;

public class ChatListFragment extends Fragment implements ChatListContract.View, ChatAdapter.OnManageChatList {
    FragmentChatListBinding binding;
    private ChatAdapter adapter;
    private ChatListContract.Presenter presenter;
    private Chat deleted;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new CharListPresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChatListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRvChat();
        initFavChat();
    }

    private void initFavChat() {
        binding.fabChat.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigate(R.id.action_chatListFragment_to_crearChatFragment));
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.load();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_chat_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chat_ordenar:
                presenter.order();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Este método iniciliza el componente recycler view
     */
    private void initRvChat() {
        //1. Será inicilizar el adapter
        adapter = new ChatAdapter(new ArrayList<>(), this);

        //2. OBLIGATORIAMENTE se debe indicar qué diseño (layout) tendrá el recyclerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        //3. Asigno el layout al recyclerView
        binding.rvChat.setLayoutManager(linearLayoutManager);

        //4. Asigno a la vista sus datos (modelo)
        binding.rvChat.setAdapter(adapter);

        //5. Asignar decoracióm a los items
        binding.rvChat.addItemDecoration(new DecorationRecyclerView(ContextCompat.getDrawable(getActivity(), R.drawable.item_decorator_chat)));
    }

    //region Método por el progress bar
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
    //endregion

    //region Método definidos por el repositorio
    @Override
    public void onFailure(String message) {

    }

    @Override
    public <T> void onSuccess(ArrayList<T> list) {
    }

    @Override
    public void onDeleteSuccess(String message) {
        Snackbar.make(getView(), message, BaseTransientBottomBar.LENGTH_SHORT).setAction("UNDO", (View.OnClickListener) view ->
            presenter.undo(deleted)
        ).show();

        adapter.delete(deleted);

        if(adapter.isEmpty()) {
            showNoData();
        }
    }

    @Override
    public void onUndoSuccess(String message) {
        adapter.undo(deleted);
    }
    //endregion

    //region Método que vienen por el PRESENTER
    @Override
    public <T> void showData(ArrayList<T> list) {
        adapter.update((ArrayList<Chat>) list);
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showDataOrder() {
        adapter.order();
    }

    @Override
    public void showDataInverseOrder() {
        adapter.inverseOrder();
    }
    //endregion

    //region Métodos por el Adapter

    @Override
    public void OnEditChat(Chat chat) {
        Toast.makeText(getContext(), "se quiere editar el chat " + chat.getNombre(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnDeleteChat(Chat chat) {
        Bundle bundle = new Bundle();
        bundle.putString(BaseDialogFragment.TITLE, "Eliminar chat");
        bundle.putString(BaseDialogFragment.MESSAGE, String.format("¿Seguro que desea borrar el chat %1$s?", chat.getNombre()));
        NavHostFragment.findNavController(this).navigate(R.id.action_chatListFragment_to_baseDialogFragment, bundle);

        getActivity().getSupportFragmentManager().setFragmentResultListener(BaseDialogFragment.REQUEST, this, (requestKey, result) -> {
            if(result.getBoolean(BaseDialogFragment.KEY_BUNDLE)) {
                deleted = chat;
                presenter.delete(chat);
            }
        });
    }
    //endregion
}