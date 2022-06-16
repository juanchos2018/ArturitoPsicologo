package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.arturitopsicologo.R;

public class HerramientasActivity extends AppCompatActivity implements View.OnClickListener {


    CardView cardCumanin,cardNeuropsicologia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herramientas);

        cardCumanin=(CardView) findViewById(R.id.cardCumanin);
        cardCumanin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardCumanin:
                startActivity(new Intent(this,CategoriaActivity.class));
                break;
        }
    }
}