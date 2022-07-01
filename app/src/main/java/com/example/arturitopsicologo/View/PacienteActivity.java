package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Presenter.PresenterFecha;
import com.example.arturitopsicologo.Presenter.PresenterPaciente;
import com.example.arturitopsicologo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PacienteActivity extends AppCompatActivity  implements View.OnClickListener {


    PresenterPaciente presenter;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    String user_id;
    String CategoriaId;
    Lectura lectura;
    Object object;
    ImageView imgfinish;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);

        reference= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        CategoriaId =getIntent().getStringExtra("categoria_id");
        id =  getIntent().getStringExtra("id");
        object =  getIntent().getSerializableExtra("object");
        presenter= new PresenterPaciente(this,reference,user_id,CategoriaId,object,id);


        imgfinish=(ImageView) findViewById(R.id.imgfinish);
        imgfinish.setOnClickListener(this);
        inputs();

    }

    @Override
    protected void onStart() {
        super.onStart();
        records();
    }
    private  void  finishs(){
        finish();
    }
    private void records() {

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerpsicologpaciente);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecycler(recyclerView,CategoriaId);
    }


    private void inputs() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgfinish:
                finishs();
                break;
        }
    }
}