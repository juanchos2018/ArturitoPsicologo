package com.example.arturitopsicologo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.arturitopsicologo.View.CategoriaActivity;
import com.example.arturitopsicologo.View.CitasActivity;
import com.example.arturitopsicologo.View.HerramientasActivity;
import com.example.arturitopsicologo.View.HorarioActivity;
import com.example.arturitopsicologo.View.PerfilActivity;
import com.example.arturitopsicologo.View.ResultadoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity  implements View.OnClickListener {

    CardView carHerramientas,cardPerfil,cardHorario,cardcitas,cardresultado;
    private DatabaseReference databaseReference;
    Button btnperfil,btnsalir;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();

        inputs();
    }

    private void inputs() {

        carHerramientas=(CardView) findViewById(R.id.carHerramientas);
        cardPerfil=(CardView) findViewById(R.id.cardPerfil);
        cardHorario=(CardView) findViewById(R.id.cardHorario);
        cardcitas=(CardView) findViewById(R.id.cardcitas);
        cardresultado=(CardView) findViewById(R.id.cardresultado);

        btnsalir=(Button)findViewById(R.id.btnsalir);
        btnsalir.setOnClickListener(this);


        cardPerfil.setOnClickListener(this);
        carHerramientas.setOnClickListener(this);
        cardHorario.setOnClickListener(this);
        cardcitas.setOnClickListener(this);
        cardresultado.setOnClickListener(this);

        //carHerramientas.setOnClickListener(this);cardresultado
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.carHerramientas:
                startActivity(new Intent(this, CategoriaActivity.class));
                break;
            case R.id.cardPerfil:

                startActivity(new Intent(this, PerfilActivity.class));
                break;
            case R.id.cardHorario:

                startActivity(new Intent(this, HorarioActivity.class));
                break;
            case R.id.cardcitas:
                startActivity(new Intent(this, CitasActivity.class));
                break;
            case R.id.cardresultado:
                startActivity(new Intent(this, ResultadoActivity.class));
                break;
            case R.id.btnsalir:

              Salir();
                break;
        }
    }

    private void  Salir(){
        mAuth.signOut();
        startActivity(new Intent(this,LoginActivity.class));
    }

}