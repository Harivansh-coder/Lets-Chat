package com.harivansh.letschat.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.harivansh.letschat.R;
import com.harivansh.letschat.databinding.FragmentProfileBinding;
import com.harivansh.letschat.model.User;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private FragmentProfileBinding fragmentProfileBinding;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("getting your data");
        progressDialog.show();


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
                        progressDialog.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"something went wrong",Toast.LENGTH_LONG).show();
                        //Getting user failed, log a message
                        Log.w(TAG, "loadPost:onCancelled", error.toException());
                    }
                });


        return fragmentProfileBinding.getRoot();
    }
}