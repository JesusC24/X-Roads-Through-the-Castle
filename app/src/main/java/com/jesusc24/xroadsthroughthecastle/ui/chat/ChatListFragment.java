package com.jesusc24.xroadsthroughthecastle.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;
import com.jesusc24.xroadsthroughthecastle.databinding.FragmentChatListBinding;
import com.jesusc24.xroadsthroughthecastle.ui.DecorationRecyclerView;
import com.jesusc24.xroadsthroughthecastle.ui.base.BaseDialogFragment;
import com.jesusc24.xroadsthroughthecastle.ui.base.PasswordDialogFragment;
import com.jesusc24.xroadsthroughthecastle.ui.base.PasswordDialogFragmentDirections;
import com.jesusc24.xroadsthroughthecastle.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class ChatListFragment extends Fragment implements ChatListContract.View, ChatAdapter.OnManageChatList, SearchView.OnQueryTextListener {
    FragmentChatListBinding binding;
    private ChatAdapter adapter;
    private ChatListContract.Presenter presenter;
    private Chat deleted;
    private PreferencesManager preferenceManager;
    private Boolean order = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new ChatListPresenter(this, new PreferencesManager(getContext()));
        preferenceManager = new PreferencesManager(getContext());
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
        initSearch();
    }

    private void initSearch() {
        binding.txtBuscar.setOnQueryTextListener(this);
    }

    private void initFavChat() {
        binding.fabChat.setOnClickListener(v -> {
            ChatListFragmentDirections.ActionChatListFragmentToCrearChatFragment action = ChatListFragmentDirections.actionChatListFragmentToCrearChatFragment(null);
            NavHostFragment.findNavController(this).navigate(action);
        });
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
                order = !order;
                presenter.order(order);
                return true;

            case R.id.menu_chat_ordenar_favorito:
                presenter.orderByStar();
                return true;

            case R.id.menu_chat_buscar:
                presenter.search();
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
        binding.includeProgressbar.llProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.includeProgressbar.llProgressBar.setVisibility(View.GONE);
    }
    //endregion

    //region Método definidos por el repositorio
    @Override
    public void onFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
    }

    @Override
    public <T> void onSuccess(List<T> list) {
    }

    @Override
    public void onDeleteSuccess(String message) {
        Snackbar.make(getView(), getString(R.string.delete_chat_success) + message, BaseTransientBottomBar.LENGTH_SHORT).setAction("UNDO", (View.OnClickListener) view ->
            presenter.undo(deleted)
        ).show();

        adapter.delete(deleted);

        if(adapter.isEmpty()) {
            showNoData();
        }
    }

    @Override
    public void onUndoSuccess() {
        Toast.makeText(getContext(), R.string.undo_success, Toast.LENGTH_SHORT).show();
        adapter.undo(deleted);
    }
    //endregion

    //region Método que vienen por el PRESENTER
    @Override
    public <T> void showData(List<T> list) {
        if(binding.llNoDataChat.getVisibility()==View.VISIBLE) {
            binding.llNoDataChat.setVisibility(View.GONE);
        }
        adapter.update((List<Chat>) list);

        if(preferenceManager.getBoolean(Constants.KEY_ORDER_CHAT)) {
            presenter.orderByStar();
        } else {
            presenter.order(order);
        }
    }

    @Override
    public void showNoData() {
        if(binding.llNoDataChat.getVisibility()==View.GONE) {
            binding.llNoDataChat.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showDataOrder() {
        adapter.order();
    }

    @Override
    public void showDataInverseOrder() {
        adapter.inverseOrder();
    }

    @Override
    public void showDataStar() {
        adapter.orderByStar();
    }

    @Override
    public void showSearch() {
        binding.rvChat.setPadding(0, getResources().getDimensionPixelSize(R.dimen._50sdp), 0, 0);
        binding.llBuscar.setVisibility(View.VISIBLE);
        binding.txtBuscar.onActionViewExpanded();
    }

    @Override public void hideSearch() {
        binding.txtBuscar.onActionViewCollapsed();
        binding.rvChat.setPadding(0, 0, 0, 0);
        binding.llBuscar.setVisibility(View.GONE);
    }

    //endregion

    //region Métodos por el Adapter

    @Override
    public void onOpenMessages(Chat chat) {
        preferenceManager.putString(Constants.KEY_FORO_ID, chat.getId());
        if(chat.getTipo().equals(Chat.PRIVADO)) {
            Bundle bundle = new Bundle();
            bundle.putString(PasswordDialogFragment.TITLE, getString(R.string.putPasswoord));
            bundle.putSerializable(PasswordDialogFragment.CHAT, chat);
            NavHostFragment.findNavController(this).navigate(R.id.action_chatListFragment_to_passwordDialogFragment, bundle);

            getActivity().getSupportFragmentManager().setFragmentResultListener(PasswordDialogFragment.REQUEST, this, (requestKey, result) -> {
                if(result.getBoolean(PasswordDialogFragment.KEY_BUNDLE)) {
                    PasswordDialogFragmentDirections.ActionPasswordDialogFragmentToMessageFragment action = PasswordDialogFragmentDirections.actionPasswordDialogFragmentToMessageFragment(chat);
                    NavHostFragment.findNavController(this).navigate(action);
                }
            });
        } else {
            ChatListFragmentDirections.ActionChatListFragmentToChatFragment action = ChatListFragmentDirections.actionChatListFragmentToChatFragment(chat);
            NavHostFragment.findNavController(this).navigate(action);
        }
    }


    @Override
    public void onDeleteChat(Chat chat) {
        if(chat.getIdUser().contentEquals(preferenceManager.getString(Constants.KEY_USER_ID))) {
            Bundle bundle = new Bundle();
            bundle.putString(BaseDialogFragment.TITLE, getString(R.string.delete_chat));
            bundle.putString(BaseDialogFragment.MESSAGE, String.format(getString(R.string.ask) + " %1$s?", chat.getNombre()));
            NavHostFragment.findNavController(this).navigate(R.id.action_chatListFragment_to_baseDialogFragment, bundle);

            getActivity().getSupportFragmentManager().setFragmentResultListener(BaseDialogFragment.REQUEST, this, (requestKey, result) -> {
                if(result.getBoolean(BaseDialogFragment.KEY_BUNDLE)) {
                    deleted = chat;
                    presenter.delete(chat);
                }
            });
        } else {
            Toast.makeText(getContext(), R.string.err_deleteChatAuth, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActiveStar(boolean status, Chat chat) {
        chat.setFavorito(status);
        presenter.updateStar(chat);
    }
    //endregion

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return true;
    }

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