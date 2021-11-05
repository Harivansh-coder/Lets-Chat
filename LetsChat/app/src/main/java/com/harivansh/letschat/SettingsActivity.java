package com.harivansh.letschat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.harivansh.letschat.databinding.ActivitySettingsBinding;
import com.harivansh.letschat.model.User;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {


    private ActivitySettingsBinding binding;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // hiding the action bar
        getSupportActionBar().hide();

        // getting firebase instances

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        // back button

        binding.settingBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // username and about implementation

        firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // getting already added data from database

                        User user = snapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getUserProfileImage())
                                .placeholder(R.drawable.profile)
                                .into(binding.settingsProfileImage);

                        binding.settingUserNameText.setText(user.getUserName());
                        binding.settingAbout.setText(user.getStatus());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        // save button
        binding.settingSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = binding.settingAbout.getText().toString();
                String userName = binding.settingUserNameText.getText().toString();

                HashMap<String, Object> objectHashMap = new HashMap<>();

                objectHashMap.put("userName", userName);
                objectHashMap.put("status", status);

                // adding new setting data to database
                firebaseDatabase.getReference().child("Users")
                        .child(FirebaseAuth.getInstance().getUid())
                        .updateChildren(objectHashMap);

                Snackbar.make(binding.settingSaveButton, "settings applied", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

        // adding profile image

        binding.settingChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);

            }
        });


    }

    // getting uri from local machine and uploading to firebase storage
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {

            Uri iFile = data.getData();

            binding.settingsProfileImage.setImageURI(iFile);


            // getting firebase storage reference
            final StorageReference storageReference = firebaseStorage.getReference().child("UserProfileImage")
                    .child(FirebaseAuth.getInstance().getUid());


            // passing the uri
            storageReference.putFile(iFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            firebaseDatabase.getReference().child("Users")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .child("userProfileImage").setValue(uri.toString());
                        }
                    });
                    Snackbar.make(binding.settingChangeImage, "Image added", BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        }
    }
}