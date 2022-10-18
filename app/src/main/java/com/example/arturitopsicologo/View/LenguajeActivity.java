package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.arturitopsicologo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LenguajeActivity extends AppCompatActivity implements View.OnClickListener {


    FloatingActionButton btnaddLenguaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lenguaje);


        inputs();
        btnaddLenguaje.setOnClickListener(this);
    }

    private void inputs() {

        btnaddLenguaje=(FloatingActionButton) findViewById(R.id.btnaddLenguaje);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case  R.id.btnaddLenguaje:
                startActivity(new Intent(this,LenguajeCrearActivity.class));
                break;
        }

    }
}