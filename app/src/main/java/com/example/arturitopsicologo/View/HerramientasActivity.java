package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.arturitopsicologo.Model.Herramienta;
import com.example.arturitopsicologo.R;

public class HerramientasActivity extends AppCompatActivity implements View.OnClickListener {


    CardView cardCumanin,cardNeuropsicologia;
    String CategoriaId="-N3iU-HERRAMIENTA";
    Button btnenviarherramienta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herramientas);

        btnenviarherramienta=(Button)findViewById(R.id.btnenviarherramienta);


        btnenviarherramienta.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnenviarherramienta:

                Herramienta herramienta = new Herramienta();
                herramienta.setCategoriaId(CategoriaId);
                herramienta.setPuntaje("0");
                herramienta.setId(ponerid());
                herramienta.setTitulo("Linea");
                herramienta.setUrl_imagen_resolv("null");
                herramienta.setEstado("null");
                herramienta.setUrl_imagen("https://firebasestorage.googleapis.com/v0/b/menudecomi.appspot.com/o/Cuadrilla%2Fpruebaline.png?alt=media&token=6689f56c-cc66-4566-9677-0d1b1fffeb99");

                Intent intent = new Intent(this,PacienteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("categoria_id",CategoriaId);
                bundle.putString("id",herramienta.getId());
                bundle.putSerializable("object",herramienta);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

                //startActivity(new Intent(this,CategoriaActivity.class));
                break;
        }
    }
    private  String ponerid(){

        String NUMEROS = "0123456789";
        String MAYUSCULAS = "ABCDEFGHIJKLMNO_PQRSTUVWXYZ";
        String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
        String id = "";
        String key=NUMEROS+MAYUSCULAS+MINUSCULAS;
        for (int i = 0; i < 9; i++) {
            id+=(key.charAt((int)(Math.random() * key.length())));
        }

        return id;

    }

}