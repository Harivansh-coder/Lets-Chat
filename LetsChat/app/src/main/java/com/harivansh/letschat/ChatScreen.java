package com.harivansh.letschat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.harivansh.letschat.databinding.ActivityChatScreenBinding;
import com.harivansh.letschat.fragment.ChatFragment;
import com.squareup.picasso.Picasso;

public class ChatScreen extends AppCompatActivity {

    private ActivityChatScreenBinding binding;

    private FirebaseDatabase firebaseDatabase;

    private FirebaseAuth firebaseAuth;

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

        String userId = firebaseAuth.getUid();
        String receiverId = getIntent().getStringExtra("userId");


        // extracting the values
        String profileImage = getIntent().getStringExtra("userProfileImg");

        Picasso.get().load(profileImage).placeholder(R.drawable.profile).into(binding.chatProfileImage);
        binding.chatscreenUsername.setText(getIntent().getStringExtra("userName"));



    }
}