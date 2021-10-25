package com.harivansh.letschat;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.harivansh.letschat.databinding.ActivitySigninBinding;

public class Signin extends AppCompatActivity {

    private ActivitySigninBinding binding;
    private FirebaseAuth fauth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        fauth = FirebaseAuth.getInstance();


        // progress dialog
        progressDialog = new ProgressDialog(Signin.this);
        progressDialog.setMessage("A moment please");


        // Login Button
        binding.signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });


        // SignUp Button
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this,SignUp.class));
                finish();
            }
        });

        // Forgot Button
        binding.forgotLs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this,ForgotPassword.class));
                finish();
            }
        });


    }


    // Sign in function
    void SignIn(){

        progressDialog.show();

        //binding.signinprogress.setVisibility(View.VISIBLE);

        String user_email = binding.userEmail.getText().toString().trim();
        String user_password = binding.userPassword.getText().toString().trim();

        if (user_email.length() != 0 && user_password.length() != 0){
//             binding.signinprogress.setVisibility(View.INVISIBLE);
//             Toast.makeText(this,user_email+" "+user_password,Toast.LENGTH_LONG).show();


            fauth.signInWithEmailAndPassword(user_email,user_password).
                    addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                if (user.isEmailVerified()){

                                    progressDialog.dismiss();

                                    Log.d("message", "signin success");
                                    Snackbar.make(binding.signinbutton,"signin successful", BaseTransientBottomBar.LENGTH_LONG).show();
                                    startActivity(new Intent(Signin.this, DashBoard.class));
                                    finish();

                                }else {
                                    fauth.signOut();
                                    verifyEmail(user_email);
                                    Snackbar.make(binding.signinbutton,"you are not a verified user, verification email has been sent",BaseTransientBottomBar.LENGTH_LONG).show();
                                }


                            }else{
                                Log.d("error","Signin failed",task.getException());
                                Snackbar.make(binding.signinbutton,task.getException().getMessage(),BaseTransientBottomBar.LENGTH_LONG).show();
                            }


                        }
                    });
        }else Snackbar.make(binding.signinbutton,"either of the fields cannot be empty",BaseTransientBottomBar.LENGTH_LONG).show();



    }

    public void verifyEmail(String email){

        FirebaseUser user = fauth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Snackbar.make(binding.signinbutton,"a verification has been sent to you", BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}



