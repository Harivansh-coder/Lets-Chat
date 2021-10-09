package com.harivansh.letschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.harivansh.letschat.databinding.ActivityForgotPasswordBinding;

public class ForgotPassword extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        firebaseAuth = FirebaseAuth.getInstance();

        // forgot password

        binding.sendFps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.emailfps.getText().toString().trim();

                if (email.length() != 0){

                    firebaseAuth.sendPasswordResetEmail(email).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Snackbar.make(binding.sendFps,"Check email to reset password",
                                        BaseTransientBottomBar.LENGTH_LONG).show();
                            }else{
                                Snackbar.make(binding.sendFps,task.getException().getMessage(),
                                        BaseTransientBottomBar.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Snackbar.make(binding.sendFps,"email cannot be empty",
                            BaseTransientBottomBar.LENGTH_LONG).show();
                }
            }
        });


        // back button
        binding.BackFps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ForgotPassword.this,Signin.class));
                finish();

            }
        });
    }
}

