package com.jesusc24.xroadsthroughthecastle.ui.bugs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.constantes.Constants;
import com.jesusc24.xroadsthroughthecastle.data.model.Bug;
import com.jesusc24.xroadsthroughthecastle.data.model.BugComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter que sirve para poder rellenar un recyclerView
 */
public class BugAdapter extends RecyclerView.Adapter<BugAdapter.ViewHolder> {

    private List<Bug> list;
    private List<Bug> listOriginal;
    private OnManageBugList listener;

    public BugAdapter(BugAdapter.OnManageBugList listener) {
        //De momento solo vamos a mostrar el nombre, esto es un ejemplo de como se verá con datos reales
        this.list = new ArrayList<>();
        this.listener = listener;
        listOriginal = new ArrayList<>();
    }

    public interface OnManageBugList {
        //Si se hace click en un bug se edita (onClickListener)
        void onShowBug(Bug bug);
        //Si se hace una pulsación larga en el bug se elimina (onLongClickListener)
        void onDeleteBug(Bug bug);
    }

    /**
     * Se llama a este método tantas veces como elementos se vizualicen en la pantalla /elementos del arraylist
     * del dispositivo movil SIEMPRE y CUANDO getItemCount >0
     */
    @NonNull
    @Override
    public BugAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bug_item, parent, false);
        return new BugAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BugAdapter.ViewHolder holder, int position) {
        holder.tvNombre.setText(list.get(position).getNombre());

        String estado = list.get(position).getEstado();

        if(Constants.KEY_BUG_ENVIADO.contentEquals(estado)) {
            holder.ivEstado.setImageResource(R.drawable.ic_action_bug_send);
        } else if(Constants.KEY_BUG_APROBADO.contentEquals(estado)) {
            holder.ivEstado.setImageResource(R.drawable.ic_action_bug_passed);
        } else if(Constants.KEY_BUG_ARREGLADO.contentEquals(estado)) {
            holder.ivEstado.setImageResource(R.drawable.ic_action_bug_result);
        } else if(Constants.KEY_BUG_DENAGADO.contentEquals(estado)) {
            holder.ivEstado.setImageResource(R.drawable.ic_action_bug_denied);
        }

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
        //Se crea los elementos que tengo hecho en el bug_item.xml
        TextView tvNombre;
        ImageView ivEstado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvName);
            ivEstado = itemView.findViewById(R.id.tvImagenEstado);
        }

        public void bind(Bug bug, OnManageBugList listener) {
            itemView.setOnClickListener(v -> {
                listener.onShowBug(bug);
            });

            itemView.setOnLongClickListener(v -> {
                listener.onDeleteBug(bug);
                return true;
            });
        }
    }

    public void update(List<Bug> list) {
        this.list.clear();
        this.list.addAll(list);
        listOriginal.clear();
        listOriginal.addAll(list);
        notifyDataSetChanged();
    }

    public void delete(Bug bug) {
        list.remove(bug);
        listOriginal.remove(bug);
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void undo(Bug bug) {
        list.add(bug);
        listOriginal.add(bug);
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

    public void orderByEstado() {
        Collections.sort(list, new BugComparator());
        notifyDataSetChanged();
    }

    public void filtrado(String buscado) {
        if(buscado.length() != 0) {
            List<Bug> collecion = list.stream()
                    .filter(i -> i.getNombre().toLowerCase().contains(buscado.toLowerCase()))
                    .collect(Collectors.toList());
            list.clear();
            list.addAll(collecion);
        } else {
            list.clear();
            list.addAll(listOriginal);
        }

        notifyDataSetChanged();
    }
}
