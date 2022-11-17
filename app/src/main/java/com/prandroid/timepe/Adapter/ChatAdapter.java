package com.prandroid.timepe.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.prandroid.timepe.Models.MessageModel;
import com.prandroid.timepe.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter{

    ArrayList<MessageModel> msgmodel;
    Context context;
    String recId;

    int SENDER_VIEW_TYPE=1;
    int RECEIVER_VIEW_TYPE=2;

    public ChatAdapter(ArrayList<MessageModel> msgmodel, Context context) {
        this.msgmodel = msgmodel;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> msgmodel, Context context, String recId) {
        this.msgmodel = msgmodel;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.sender,parent,false);
            return new SenderViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.receiver,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(msgmodel.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }else{
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = msgmodel.get(position);


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String senderRoom = FirebaseAuth.getInstance().getUid() + recId;
                                database.getReference().child("Chats").child(messageModel.getMessageId())
                                        .setValue(null);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                return false;
            }
        });

        if(holder.getClass()== SenderViewHolder.class){
            ((SenderViewHolder)holder).sendermsg.setText(messageModel.getMessage());
            Date date = new Date(messageModel.getTimestamp());
            SimpleDateFormat dateformat = new SimpleDateFormat("h:mm a");
            String strdate= dateformat.format(date);
            ((SenderViewHolder)holder).sendertime.setText(strdate.toString());
        }else{
            ((ReceiverViewHolder)holder).receivermsg.setText(messageModel.getMessage());
            Date date = new Date(messageModel.getTimestamp());
            SimpleDateFormat dateformat = new SimpleDateFormat("h:mm a");
            String strdate= dateformat.format(date);
            ((ReceiverViewHolder)holder).receivetime.setText(strdate.toString());
        }
    }

    @Override
    public int getItemCount() {
        return msgmodel.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView receivermsg, receivetime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receivermsg = itemView.findViewById(R.id.recievertxt);
            receivetime = itemView.findViewById(R.id.recievertime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder{

        TextView sendermsg,sendertime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            sendermsg = itemView.findViewById(R.id.sendertext);
            sendertime = itemView.findViewById(R.id.sendertime);
        }
    }
}
