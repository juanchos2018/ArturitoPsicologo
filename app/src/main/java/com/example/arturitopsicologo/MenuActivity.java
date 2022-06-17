package com.example.arturitopsicologo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.arturitopsicologo.View.HerramientasActivity;
import com.example.arturitopsicologo.View.HorarioActivity;
import com.example.arturitopsicologo.View.PerfilActivity;
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

    CardView carHerramientas,cardPerfil,cardHorario;
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

        btnsalir=(Button)findViewById(R.id.btnsalir);
        btnsalir.setOnClickListener(this);


        cardPerfil.setOnClickListener(this);
        carHerramientas.setOnClickListener(this);
        cardHorario.setOnClickListener(this);

        //carHerramientas.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.carHerramientas:
                startActivity(new Intent(this, HerramientasActivity.class));
                break;
            case R.id.cardPerfil:

                startActivity(new Intent(this, PerfilActivity.class));
                break;
            case R.id.cardHorario:

                startActivity(new Intent(this, HorarioActivity.class));
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

    
    private  void  kmar(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Categoria");
        String key =databaseReference.push().getKey();
        Map<String,Object> categoria= new HashMap<>();
        categoria.put("nombre","Lectura ");
        categoria.put("photo","default");
        categoria.put("tipo","tipo");
        categoria.put("id",key);

        databaseReference.child(key).setValue(categoria).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MenuActivity.this, "create success", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(MenuActivity.this, "err "+e.getMessage(), Toast.LENGTH_SHORT).show();
               
            }
        });
    }
}