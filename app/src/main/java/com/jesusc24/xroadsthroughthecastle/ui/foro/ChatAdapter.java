package com.jesusc24.xroadsthroughthecastle.ui.foro;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.model.Chat;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    private ArrayList<Chat> list;

    public ChatAdapter() {
        this.list = new ArrayList<>();
        list.add(new Chat("Primer chat", "Público", null, 1));
        list.add(new Chat("Segundo chat", "Público", null, 2));
        list.add(new Chat("Tercer chat", "Público", null, 3));
        list.add(new Chat("Cuarto chat", "Público", null, 4));
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
        //Se crea los elementos que tenemos hecho en el depedency_item.xml
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
    }
}