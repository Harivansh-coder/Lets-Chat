package com.harivansh.letschat.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harivansh.letschat.R;
import com.harivansh.letschat.databinding.FragmentChatBinding;
import com.harivansh.letschat.databinding.FragmentProfileBinding;
import com.harivansh.letschat.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {



    public ProfileFragment() {
        // Required empty public constructor
    }


    private FragmentProfileBinding fragmentProfileBinding;

    private DatabaseReference databaseReference;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        // reading data from firebase

        databaseReference.child("Users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // Get User object and use the values to update the UI
                        User user = snapshot.getValue(User.class);

                        Picasso.get()
                                .load(user.getUserProfileImage())
                                .placeholder(R.drawable.profile)
                                .into(fragmentProfileBinding.profilePageImage);

                        fragmentProfileBinding.usernameTextView.setText(user.getUserName());
                        fragmentProfileBinding.profileAboutTextview.setText(user.getStatus());
                        fragmentProfileBinding.profileEmailTextview.setText(user.getUserEmail());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                         //Getting user failed, log a message
                        Log.w("profile database error", "loadPost:onCancelled", error.toException());
                    }
                });






        return fragmentProfileBinding.getRoot();
    }
}