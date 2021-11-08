package com.harivansh.letschat;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.harivansh.letschat.databinding.ActivitySignUpBinding;
import com.harivansh.letschat.model.User;

public class SignUp extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    private ActivitySignUpBinding binding;
    private FirebaseAuth fauth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        // getting firebase instance
        fauth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");


        // progress dialog
        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("A moment please");


        // sign Up button
        binding.signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });


    }

    void signUp() {
        progressDialog.show();

        // Getting user data
        String user_name = binding.signUpName.getText().toString().trim();
        String user_email = binding.signUpEmail.getText().toString().trim();
        String user_pass = binding.signUpPass.getText().toString().trim();
        String user_conPass = binding.signUpConPass.getText().toString().trim();

        if (user_name.length() != 0 && user_email.length() != 0 && user_pass.length() != 0 && user_conPass.length() != 0) {
            if (user_pass.equals(user_conPass)) {

                // user creation
                fauth.createUserWithEmailAndPassword(user_email, user_conPass).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    User user = new User(user_name, user_email);

                                    String userId = task.getResult().getUser().getUid(); // getting the userid from auth firebase

                                    //Toast.makeText(SignUp.this,userId,Toast.LENGTH_LONG).show();
                                    firebaseDatabase.getReference().child("Users").child(userId).setValue(user); // storing user data

                                    progressDialog.dismiss();

                                    verifyEmail(user_email);


                                    Log.d("message", "signup success");
                                    Snackbar.make(binding.signupbutton, "account created successfully", BaseTransientBottomBar.LENGTH_LONG).show();
//                            startActivity(new Intent(SignUp.this, Signin.class));
//                            finish();

                                } else {
                                    progressDialog.dismiss();

                                    Log.d("error", "Signup failed", task.getException());
                                    Snackbar.make(binding.signupbutton, task.getException().getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                                }
                            }
                        });

            } else {
                progressDialog.dismiss();
                Snackbar.make(binding.signupbutton, "both password fields must be same", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        } else {
            progressDialog.dismiss();
            Snackbar.make(binding.signupbutton, "either of the field cannot be empty", BaseTransientBottomBar.LENGTH_LONG).show();
        }

    }

    public void verifyEmail(String email) {

        FirebaseUser user = fauth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Snackbar.make(binding.signupbutton, "a verification has been sent to you", BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void backButton(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
