package com.harivansh.letschat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.harivansh.letschat.R;
import com.harivansh.letschat.model.Messages;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter{

    ArrayList<Messages> messagesArrayList;

    Context context;

    public ChatAdapter(ArrayList<Messages> messagesArrayList, Context context) {
        this.messagesArrayList = messagesArrayList;
        this.context = context;
    }

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SENDER_VIEW_TYPE){

            View view = LayoutInflater.from(context).inflate(R.layout.sender_message_layout,parent,false);
            return new SenderViewHolder(view);
        }else {

            View view = LayoutInflater.from(context).inflate(R.layout.receiver_message_layout,parent,false);
            return new ReceiverViewHolder(view);

        }


    }

    @Override
    public int getItemViewType(int position) {

        if (messagesArrayList.get(position).getMessageId().equals(FirebaseAuth.getInstance().getUid())){

            return SENDER_VIEW_TYPE;

        }else{

            return RECEIVER_VIEW_TYPE;

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Messages messages = messagesArrayList.get(position);

        if (holder.getClass() == SenderViewHolder.class){
            ((SenderViewHolder) holder).senderMessage.setText(messages.getMessageText());
        }else{
            ((ReceiverViewHolder) holder).receiverMessage.setText(messages.getMessageText());
        }

    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{

        TextView receiverMessage, receiverTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverMessage = itemView.findViewById(R.id.receiver_message);
            receiverTime = itemView.findViewById(R.id.receiver_time);
        }
    }


    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView senderMessage, senderTime;


        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessage = itemView.findViewById(R.id.sender_message);
            senderTime = itemView.findViewById(R.id.sender_time);

        }
    }
}
