package com.jesusc24.xroadsthroughthecastle.ui.foro.message;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jesusc24.xroadsthroughthecastle.R;
import com.jesusc24.xroadsthroughthecastle.data.model.Mensaje;
import com.jesusc24.xroadsthroughthecastle.databinding.ItemContainerReceivedMessageBinding;
import com.jesusc24.xroadsthroughthecastle.databinding.ItemContainerSentMessageBinding;
import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Mensaje> list;
    private String senderId;

    public MessageAdapter(List<Mensaje> list, String senderId) {
        this.list = list;
        this.senderId = senderId;
    }

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVE = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SENT) {
            return new SentMessage(ItemContainerSentMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        } else {
            return new ReceivedMessage(ItemContainerReceivedMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_SENT) {
            ((SentMessage)holder).setData(list.get(position));
        } else {
            ((ReceivedMessage)holder).setData(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getEnvioId().equals(senderId)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVE;
        }
    }

    public void update(List<Mensaje> newlist) {
        list.addAll(newlist);
        notifyDataSetChanged();
    }

    public void newMessage(List<Mensaje> newList) {
        for(int i=list.size(); i<newList.size(); i++) {
            list.add(newList.get(i));
        }
        notifyDataSetChanged();
    }

    public class SentMessage extends RecyclerView.ViewHolder {

        private final ItemContainerSentMessageBinding binding;

        public SentMessage(ItemContainerSentMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setData(Mensaje mensaje) {
            binding.textMessage.setText(mensaje.getTexto());
            binding.textDateTime.setText(CommonUtils.getReadableDateTime(mensaje.getFecha_envio()));
        }
    }

    public class ReceivedMessage extends RecyclerView.ViewHolder {

        private final ItemContainerReceivedMessageBinding binding;

        public ReceivedMessage(ItemContainerReceivedMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setData(Mensaje mensaje) {
            binding.textUser.setText(mensaje.getUser());
            binding.textMessage.setText(mensaje.getTexto());
            binding.textDateTime.setText(CommonUtils.getReadableDateTime(mensaje.getFecha_envio()));

            if(mensaje.getImagen() != null) {
                binding.imageProfile.setImageBitmap(CommonUtils.decodeImage(mensaje.getImagen()));
            } else {
                binding.imageProfile.setImageResource(R.drawable.img_logo);
            }
        }

    }


}
