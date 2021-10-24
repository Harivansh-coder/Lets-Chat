package com.harivansh.letschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harivansh.letschat.adapter.ChatAdapter;
import com.harivansh.letschat.databinding.ActivityChatScreenBinding;
import com.harivansh.letschat.fragment.ChatFragment;
import com.harivansh.letschat.model.Messages;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatScreen extends AppCompatActivity {

    private ActivityChatScreenBinding binding;

    private FirebaseDatabase firebaseDatabase;

    private FirebaseAuth firebaseAuth;

    private ArrayList<Messages> messageArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().hide();
        // getting database and user auth reference

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        // back button
        binding.chatscreenBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ChatScreen.this, DashBoard.class));
                finish();

            }
        });

        // user data from firebase

        final String userId = firebaseAuth.getUid();
        String receiverId = getIntent().getStringExtra("userId");


        // extracting the values
        String profileImage = getIntent().getStringExtra("userProfileImg");

        Picasso.get().load(profileImage).placeholder(R.drawable.profile).into(binding.chatProfileImage);
        binding.chatscreenUsername.setText(getIntent().getStringExtra("userName"));


        // chatting data implementation

        messageArrayList = new ArrayList<>();

        final ChatAdapter chatAdapter = new ChatAdapter(messageArrayList, this);





        // chatting firebase backend

        final String senderChatId = userId + receiverId;
        final String receiverChatId = receiverId + userId;

        // filling the chat bubbles

        firebaseDatabase.getReference().child("Chats")
                .child(senderChatId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageArrayList.clear();
                        for ( DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Messages messages = dataSnapshot.getValue(Messages.class);
                            messageArrayList.add(messages);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        chatAdapter.notifyDataSetChanged();
        binding.chatRecycleView.setAdapter(chatAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatRecycleView.setLayoutManager(linearLayoutManager);



        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatMessage = binding.chatMessage.getText().toString();

                final Messages messages = new Messages(userId, chatMessage);
                messages.setMessageTime(new Date().getTime());
                binding.chatMessage.setText("");

                firebaseDatabase.getReference().child("Chats")
                        .child(senderChatId)
                        .push()
                        .setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        firebaseDatabase.getReference().child("Chats")
                                .child(receiverChatId)
                                .push()
                                .setValue(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });

            }
        });



    }

}