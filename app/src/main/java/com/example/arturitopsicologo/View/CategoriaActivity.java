package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.arturitopsicologo.R;

public class CategoriaActivity extends AppCompatActivity implements View.OnClickListener {


    CardView cardlectura;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);


        cardlectura=(CardView) findViewById(R.id.cardlectura);
        cardlectura.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch ( view.getId()){
            case  R.id.cardlectura:
                startActivity(new Intent(this,LecturasActivity.class));
                break;
        }
    }
}