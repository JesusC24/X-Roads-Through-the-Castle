package com.jesusc24.xroadsthroughthecastle.ui.foro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Adapter que sirve para poder rellenar un recyclerView
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private ArrayList<Chat> list;
    private OnManageChatList listener;

    public ChatAdapter(ArrayList<Chat> list, OnManageChatList listener) {
        this.list = list;
        this.listener = listener;
    }

    public interface OnManageChatList {
        //Si se hace click en una dependencia se edita (onClickListener)
        void OnEditChat(Chat chat);
        //Si se hace una pulsación larga en la dependencia se elimina (onLongClickListener)
        void OnDeleteChat(Chat chat);
    }

    /**
     * Se llama a este método tantas veces como elementos se vizualicen en la pantalla /elementos del arraylist
     * del dispositivo movil SIEMPRE y CUANDO getItemCount >0
     */
    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        holder.tvNombre.setText(list.get(position).getNombre());
        holder.tvDescripcion.setText(list.get(position).getDescripcion());
        holder.bind(list.get(position), listener);
    }

    /**
     * Este método devuelve el número de elementos Adapter. Y es utilizado por el sistema operativo
     * cuando se inicializa el componente recyclerView. Si se deja a 0, NUNCA se muestran
     * los elementos del Adapter en el RecyclerView ya que no se llama al método onCreateViewHolder
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Se crea los elementos que tenemos hecho en el chat_item.xml
        TextView tvNombre;
        TextView tvDescripcion;
        ImageButton ibStar;
        ImageView imgChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvName);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            ibStar = itemView.findViewById(R.id.ibStar);
            imgChat = itemView.findViewById(R.id.imgChat);
        }

        public void bind(Chat chat, OnManageChatList listener) {
            itemView.setOnClickListener(v -> {
                listener.OnEditChat(chat);
            });

            itemView.setOnLongClickListener(v -> {
                listener.OnDeleteChat(chat);
                return true;
            });
        }
    }

    public void update(ArrayList<Chat> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void delete(Chat chat) {
        list.remove(chat);
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void undo(Chat chat) {
        list.add(chat);
        notifyDataSetChanged();
    }

    public void order() {
        Collections.sort(list);
        notifyDataSetChanged();
    }

    public void inverseOrder() {
        Collections.reverse(list);
        notifyDataSetChanged();
    }
}