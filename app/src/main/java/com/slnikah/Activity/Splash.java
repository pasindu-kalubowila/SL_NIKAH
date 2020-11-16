package com.slnikah.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.slnikah.Model.Users;
import com.slnikah.R;

public class Splash extends AppCompatActivity {
    FirebaseUser firebaseUser;

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int SPLASH_TIME_OUT = 1000;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isNetworkConnected()) {
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser != null) {
                        redirectToHome();
                    } else {
                        Intent intent = new Intent(Splash.this, Login.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(Splash.this, "No Network Connection. Check Your Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void redirectToHome() {
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap1 : dataSnapshot.getChildren()) {
                    Users users= (Users) snap1.getValue(Users.class);
                    if (firebaseUser.getEmail().equals(users.getEmail())){
                        if (users.getAccess().equals("admin")) {
                            Intent intent = new Intent(Splash.this, MainActivity2.class);
                            startActivity(intent);
                            finish();
                            break;
                        } else if (users.getAccess().equals("customer")) {
                            Intent intent = new Intent(Splash.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    }

                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });
    }
}