package com.example.arturitopsicologo.Presenter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arturitopsicologo.Adapter.AdapterCitas;
import com.example.arturitopsicologo.Adapter.AdapterHistorial;
import com.example.arturitopsicologo.Adapter.AdapterResultados;
import com.example.arturitopsicologo.Model.Historial;
import com.example.arturitopsicologo.Model.PsicoloPaciente;
import com.example.arturitopsicologo.Model.Resultado;
import com.example.arturitopsicologo.Model.Resultados;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class PresenterResultado {


    private Context mContext;
    private DatabaseReference databaseReference;
    private AdapterResultados adapter;
    private AdapterHistorial adapter2;
    String psicologo_id,paciente_id;


    public PresenterResultado(Context mContext, DatabaseReference databaseReference,String paciente_id) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.paciente_id=paciente_id;
    }

    public  void cargarRecycler(RecyclerView recyclerView){
        databaseReference.child("Resultado").child(paciente_id).addValueEventListener(new ValueEventListener() {
            ArrayList<Resultados> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Resultados model=item.getValue(Resultados.class);
                    lista.add(model);
                }
                adapter= new AdapterResultados(lista, mContext);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    public  void cargarHistorial(RecyclerView recyclerView){
        databaseReference.child("Historial").child(paciente_id).addValueEventListener(new ValueEventListener() {
            ArrayList<Historial> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Historial model=item.getValue(Historial.class);
                    lista.add(model);
                }
                adapter2= new AdapterHistorial(lista, mContext);
                recyclerView.setAdapter(adapter2);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}
