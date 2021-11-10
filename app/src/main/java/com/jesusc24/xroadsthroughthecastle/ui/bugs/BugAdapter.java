package com.jesusc24.xroadsthroughthecastle.ui.bugs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.model.Bug;

import java.util.ArrayList;

public class BugAdapter extends RecyclerView.Adapter<BugAdapter.ViewHolder> {

    private ArrayList<Bug> list;

    public BugAdapter() {
        this.list = new ArrayList<>();
        list.add(new Bug("Primer bug"));
        list.add(new Bug("Segundo bug"));
        list.add(new Bug("Tercer bug"));
        list.add(new Bug("Cuarto bug"));
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvName);
        }
    }
}
