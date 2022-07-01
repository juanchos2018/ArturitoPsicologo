package com.example.arturitopsicologo.Presenter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arturitopsicologo.Adapter.AdapterCitas;
import com.example.arturitopsicologo.Adapter.AdapterLectura;
import com.example.arturitopsicologo.Adapter.AdapterPsicologoPaciente;
import com.example.arturitopsicologo.Interface.InterfaceClick;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.PsicoloPaciente;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class PresenterCitas {

    private Context mContext;
    private DatabaseReference databaseReference;
    private AdapterCitas adapter;
    String psicologo_id;

    public PresenterCitas(Context mContext, DatabaseReference databaseReference,String psicologo_id) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.psicologo_id=psicologo_id;
    }

    public  void cargarRecycler(RecyclerView recyclerView){
        databaseReference.child("PsicologoPaciente").child(psicologo_id).addValueEventListener(new ValueEventListener() {
            ArrayList<PsicoloPaciente> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    PsicoloPaciente model=item.getValue(PsicoloPaciente.class);
                    lista.add(model);
                }
                adapter= new AdapterCitas(lista, mContext);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
