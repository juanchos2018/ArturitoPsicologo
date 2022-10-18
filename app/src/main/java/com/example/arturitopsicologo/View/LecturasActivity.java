package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.arturitopsicologo.Presenter.PresenterLectura;
import com.example.arturitopsicologo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LecturasActivity extends AppCompatActivity implements View.OnClickListener {


    PresenterLectura presenter;
    private DatabaseReference reference;

    String CategoriaId= "-N3iTyAojHIFohK3ft4U";

    private FirebaseAuth mAuth;
    ImageView imgfinish;

    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturas);

        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterLectura(this,reference,user_id,CategoriaId);

        imgfinish=(ImageView) findViewById(R.id.imgfinish);
        imgfinish.setOnClickListener(this);
        FloatingActionButton btnaddlectura = (FloatingActionButton)findViewById(R.id.btnaddlectura);
        btnaddlectura.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnaddlectura:
                Intent intent = new Intent(this,LecturaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id","");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.imgfinish:
                finishs();
                break;
        }
    }

    private  void  finishs(){
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        records();
    }



    private  void  records(){
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recylerlecturas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecycler(recyclerView,CategoriaId);
    }

}