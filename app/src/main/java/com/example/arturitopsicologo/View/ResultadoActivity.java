package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arturitopsicologo.Interface.InterfaceTaller;
import com.example.arturitopsicologo.Model.Atencion;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.Memoria;
import com.example.arturitopsicologo.Model.Resultado;
import com.example.arturitopsicologo.Presenter.PresenterPaciente;
import com.example.arturitopsicologo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultadoActivity extends AppCompatActivity implements InterfaceTaller {



    PresenterPaciente presenter;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    String user_id;
    String CategoriaId;
    Lectura lectura;
    Object object;
    String id;

    android.app.AlertDialog.Builder builder;
    AlertDialog alert;
    ImageView imgfinish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);


        reference= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        presenter= new PresenterPaciente(this,reference,user_id,CategoriaId,object,id);

        imgfinish=(ImageView) findViewById(R.id.imgfinish);

        imgfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishs();
            }
        });
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

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerpaciente);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecyclerDos(recyclerView,CategoriaId);
    }



    @Override
    public void onCallbackLectura(Lectura value) {

    }

    @Override
    public void onCallbackAtencion(Atencion value) {

    }

    @Override
    public void onCallbackMemoria(Memoria value) {

    }

    @Override
    public Context getContext() {
        return this;
    }
}