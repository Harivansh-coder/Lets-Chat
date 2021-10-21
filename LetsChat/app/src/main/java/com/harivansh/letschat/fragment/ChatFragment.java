package com.harivansh.letschat.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.harivansh.letschat.adapter.ProfileAdapter;
import com.harivansh.letschat.databinding.FragmentChatBinding;
import com.harivansh.letschat.model.User;

import java.util.ArrayList;


public class ChatFragment extends Fragment {


    public ChatFragment() {

    }

    private FragmentChatBinding fragmentChatBinding;
    private ArrayList<User> userArrayList ;
    private FirebaseDatabase firebaseDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentChatBinding = FragmentChatBinding.inflate(inflater,container,false);

        // users profile list
        userArrayList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();

        ProfileAdapter profileAdapter = new ProfileAdapter(userArrayList, getContext());
        fragmentChatBinding.userChatRecycleView.setAdapter(profileAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentChatBinding.userChatRecycleView.setLayoutManager(layoutManager);



        firebaseDatabase.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for( DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    user.setUserId(dataSnapshot.getKey());
                    userArrayList.add(user);
                }

                profileAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return fragmentChatBinding.getRoot();
    }


}