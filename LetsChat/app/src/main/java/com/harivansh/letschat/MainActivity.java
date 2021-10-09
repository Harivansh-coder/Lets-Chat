package com.harivansh.letschat;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.harivansh.letschat.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // firebase instance
        fauth = FirebaseAuth.getInstance();

        // Thread for loadscreen
        Thread LoadScreen = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1000);  //Delay of 1 seconds
                } catch (Exception e) {
                    Log.d(TAG,e.getMessage());
                } finally {


                    startActivity(new Intent(MainActivity.this, Signin.class));
                    finish();

                    FirebaseUser user = fauth.getCurrentUser();
                    if (user != null){
                        startActivity(new Intent(MainActivity.this, DashBoard.class));
                        finish();
                    }else{
                        startActivity(new Intent(MainActivity.this, Signin.class));
                        finish();
                    }


                }
            }
        };
        LoadScreen.start();
    }
}