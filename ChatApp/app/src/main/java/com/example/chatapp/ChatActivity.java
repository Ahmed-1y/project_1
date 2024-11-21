package com.example.chatapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {


    String receiverid, receivername, senderid, sendername, senderroom, receiverroom;
    DatabaseReference dbReferenceSender, dbReferenceReceiver, userReference;
    ImageView sendbtn;
    EditText message;
    RecyclerView recyclerView;
    MessageAdaptor messageAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        sendbtn = findViewById(R.id.sendbtn);
        message = findViewById(R.id.edit_msg);
        recyclerView = findViewById(R.id.recycler);
        messageAdaptor = new MessageAdaptor(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userReference = FirebaseDatabase.getInstance().getReference().child("users");
        receiverid = getIntent().getStringExtra("id");
        receivername = getIntent().getStringExtra("name");


        getSupportActionBar().setTitle(receivername);

        if (receiverid != null) {
            senderroom = FirebaseAuth.getInstance().getUid() + receiverid;
            receiverroom = receiverid + FirebaseAuth.getInstance().getUid();

        }


        dbReferenceSender=FirebaseDatabase.getInstance().getReference().child("Chats").child(senderroom);
        dbReferenceReceiver=FirebaseDatabase.getInstance().getReference().child("Chats").child(receiverroom);
        dbReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<MessageModel> messageModelList=new ArrayList<>();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MessageModel messageModel=dataSnapshot.getValue(MessageModel.class);
                    messageModelList.add(messageModel);
                }
                messageAdaptor.clear();
                for (MessageModel messageModel:messageModelList){
                    messageAdaptor.add(messageModel);
                }
//                Log.d("werwerwerwerwer", String.valueOf(messageModelList.size()));
//                List<MessageModel> list = messageAdaptor.getmessageModelList();
                messageAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        dbReferenceReceiver.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                messageAdaptor.clear();
//                List<MessageModel> messageModelList=new ArrayList<>();
//                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    MessageModel messageModel=dataSnapshot.getValue(MessageModel.class);
//                    messageModelList.add(messageModel);
//                }
//
//                for (MessageModel messageModel:messageModelList){
//                    messageAdaptor.add(messageModel);
//                }
//                Log.d("werwerwerwerwer", String.valueOf(messageModelList.size()));
//                List<MessageModel> list = messageAdaptor.getmessageModelList();
//                messageAdaptor.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg=message.getText().toString();
                if(msg.trim().length()>0){
                    SendMessage(msg);
                }else {
                    Toast.makeText(ChatActivity.this, "Something error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recyclerView.setAdapter(messageAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void SendMessage(String Message) {
        String messagid= UUID.randomUUID().toString();
        MessageModel messageModel=new MessageModel(messagid,FirebaseAuth.getInstance().getUid(),Message);
        messageAdaptor.add(messageModel);
        dbReferenceSender.child(messagid).setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        dbReferenceReceiver.child(messagid).setValue(messageModel);
        recyclerView.scrollToPosition(messageAdaptor.getItemCount()-1);
        message.setText("");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_manu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            return true;
        } else {
            return false;
        }
    }
}