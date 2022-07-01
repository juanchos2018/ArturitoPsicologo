package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Presenter.PresenterCitas;
import com.example.arturitopsicologo.Presenter.PresenterPaciente;
import com.example.arturitopsicologo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CitasActivity extends AppCompatActivity {


    PresenterCitas presenter;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);


        reference= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();


        presenter= new PresenterCitas(this,reference,user_id);


    }

    @Override
    protected void onStart() {
        super.onStart();
        records();
    }

    private void records() {

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerCitas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecycler(recyclerView);
    }


}