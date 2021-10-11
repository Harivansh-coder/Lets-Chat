package com.harivansh.letschat;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.harivansh.letschat.databinding.ActivityDashBoardBinding;

public class DashBoard extends AppCompatActivity {

    private ActivityDashBoardBinding binding;

    private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // firebase instance
        fauth = FirebaseAuth.getInstance();



    }


    // menu created
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.signout:
                signOut();
                break;

            case R.id.settings:
                Toast.makeText(this,"settings",Toast.LENGTH_LONG).show();
                break;
        }

        return true;
    }

    // sign out function
    public void signOut () {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(DashBoard.this);
        builder1.setMessage("Do you really want to sign out");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        fauth.signOut();
                        startActivity(new Intent(DashBoard.this, Signin.class));
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}



