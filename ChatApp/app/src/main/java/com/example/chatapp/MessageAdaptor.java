package com.example.chatapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdaptor extends RecyclerView.Adapter<MessageAdaptor.MyViewHolder> {


    private static final int view_type_sent = 1;
    private static final int view_type_receive = 2;

    private Context context;

    private List<MessageModel> messageModelList;

    public MessageAdaptor(Context context) {
        this.context = context;
        this.messageModelList = new ArrayList<>();
    }

    public void add(MessageModel MessageModel) {
        messageModelList.add(MessageModel);
        notifyItemInserted(messageModelList.size() - 1);
    }

    public void clear() {
        messageModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Log.d("asdasdasdasdadsasd", String.valueOf(viewType));

        if (viewType == view_type_sent) {
            View view = LayoutInflater.from(context).inflate(R.layout.message_row_sended, parent, false);
            return new MessageAdaptor.MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.message_row_received, parent, false);
            return new MessageAdaptor.MyViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdaptor.MyViewHolder parent, int position) {

        MessageModel MessageModel = messageModelList.get(position);
        Log.d("erwersdfcxvxcv", messageModelList.get(position).messageid);
        Log.d("erwersdfcxvxcv", messageModelList.get(position).senderid);
        if (MessageModel.getSenderid().equals(FirebaseAuth.getInstance().getUid())) {
            parent.TextSendMessage.setText(MessageModel.getMessage());
        } else {
            parent.TextReceiveMeassge.setText(MessageModel.getMessage());
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (messageModelList.get(position).getSenderid().equals(FirebaseAuth.getInstance().getUid())) {
            return view_type_sent;
        } else {
            return view_type_receive;
        }
    }

    public List<MessageModel> getmessageModelList() {
        return messageModelList;
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView TextSendMessage, TextReceiveMeassge;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            TextSendMessage = itemView.findViewById(R.id.textviewsendmesssage);
            TextReceiveMeassge = itemView.findViewById(R.id.textviewreceivedmesssage);
        }
    }
}
   