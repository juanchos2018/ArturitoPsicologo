package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.arturitopsicologo.R;

public class CategoriaActivity extends AppCompatActivity implements View.OnClickListener {


    CardView cardlectura,carAtencion,cardmemoria,cardherramienta,carLenguaje;
    ImageView imgfinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);


        cardlectura=(CardView) findViewById(R.id.cardlectura);
        carAtencion=(CardView) findViewById(R.id.carAtencion);
        cardmemoria=(CardView) findViewById(R.id.cardmemoria);
        cardherramienta=(CardView) findViewById(R.id.cardherramienta);
        carLenguaje=(CardView) findViewById(R.id.carLenguaje);

        imgfinish=(ImageView) findViewById(R.id.imgfinish);

        cardlectura.setOnClickListener(this);
        carAtencion.setOnClickListener(this);
        cardmemoria.setOnClickListener(this);
        cardherramienta.setOnClickListener(this);
        carLenguaje.setOnClickListener(this);
        imgfinish.setOnClickListener(this);

    }
    private  void  finishs(){
        finish();
    }
    @Override
    public void onClick(View view) {

        switch ( view.getId()){
            case  R.id.cardlectura:
                startActivity(new Intent(this,LecturasActivity.class));
                break;
            case R.id.carAtencion:
                startActivity(new Intent(this,AtencionActivity.class));
                break;
            case R.id.cardmemoria:
                startActivity(new Intent(this,MemoriaActivity.class));
                break;
            case R.id.cardherramienta:
                startActivity(new Intent(this,HerramientasActivity.class));
                break;
            case R.id.carLenguaje:
                startActivity(new Intent(this,LenguajeActivity.class));
                break;
            case R.id.imgfinish:
                finishs();
                break;


        }
    }
}