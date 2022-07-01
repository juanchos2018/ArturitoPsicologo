package com.example.arturitopsicologo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

      //  Date currentTime = Calendar.getInstance().getTime();
        //Toast.makeText(this, currentTime.toString(), Toast.LENGTH_SHORT).show();

        //Log.e("currendate",currentTime.toString());
       // Log.e("currendate2",currentDateandTime);
        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currentUser == null ){
                            mAuth.signOut();
                            logOutUser();
                        }else{
                            startActivity(new Intent(SplashActivity.this,MenuActivity.class));
                        }
                    }
                }, 1500);

    }

    private void logOutUser() {

        Intent loginIntent =  new Intent(SplashActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }
}