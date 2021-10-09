package com.harivansh.letschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);


        fauth = FirebaseAuth.getInstance();


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

                                    Log.d("message", "signin success");
                                    Snackbar.make(binding.signinbutton,"signin successful", BaseTransientBottomBar.LENGTH_LONG).show();
                                    startActivity(new Intent(Signin.this, DashBoard.class));
                                    finish();

                                }else {
                                    fauth.signOut();
                                    Snackbar.make(binding.signinbutton,"please verify your self",BaseTransientBottomBar.LENGTH_LONG).show();
                                }


                            }else{
                                Log.d("error","Signin failed",task.getException());
                                Snackbar.make(binding.signinbutton,task.getException().getMessage(),BaseTransientBottomBar.LENGTH_LONG).show();
                            }


                        }
                    });



        }else Snackbar.make(binding.signinbutton,"either of the fields cannot be empty",BaseTransientBottomBar.LENGTH_LONG).show();



    }


}



