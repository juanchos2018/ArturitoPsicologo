package com.example.arturitopsicologo.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.arturitopsicologo.Adapter.AdapterPacientes2;
import com.example.arturitopsicologo.Adapter.AdapterResultados;
import com.example.arturitopsicologo.Fragment.BottonSheetFragment;
import com.example.arturitopsicologo.Fragment.HistorialFragment;
import com.example.arturitopsicologo.Interface.InterfaceHistorial;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.PsicoloPaciente;
import com.example.arturitopsicologo.Model.Resultados;
import com.example.arturitopsicologo.Presenter.PresenterPaciente;
import com.example.arturitopsicologo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class HistorialActivity extends AppCompatActivity  implements InterfaceHistorial {


    PresenterPaciente presenter;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    String user_id;
    String CategoriaId;
    Lectura lectura;
    Object object;
    ImageView imgfinish;
    private AdapterPacientes2 adapter3;

    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);


        reference= FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        presenter= new PresenterPaciente(this,reference,user_id,"","","");



    }




    @Override
    protected void onStart() {
        super.onStart();
        records();
    }
    private void records() {

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerpsicologpaciente);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //presenter.cargarRecycler2(recyclerView,CategoriaId);
        cargarRecycler2(recyclerView,"");

    }
    public  void cargarHistorial(RecyclerView recyclerView){

    }

    private void resutladis(){
//        BottonSheetFragment bottomSheetDialog = BottonSheetFragment.newInstance();
//        bottomSheetDialog.paciente_id=paciente_id;
//        bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
    }


    public  void cargarRecycler2(RecyclerView recyclerView,String CategoriaId){
        reference.child("PsicologoPaciente").child(user_id).addValueEventListener(new ValueEventListener() {
            ArrayList<PsicoloPaciente> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    PsicoloPaciente model=item.getValue(PsicoloPaciente.class);
                    lista.add(model);
                }
                adapter3= new AdapterPacientes2(lista, getApplicationContext());
                recyclerView.setAdapter(adapter3);
                adapter3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String pacinnte_id=lista.get(recyclerView.getChildAdapterPosition(view)).getPaciente_id();
                        HistorialFragment bottomSheetDialog = HistorialFragment.newInstance();
                        bottomSheetDialog.paciente_id=pacinnte_id;
                        bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }



    @Override
    public void getId(String id) {
        HistorialFragment bottomSheetDialog = HistorialFragment.newInstance();
        bottomSheetDialog.paciente_id=id;
        bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
    }

    @Override
    public Context getContext() {

        return this;
    }
}