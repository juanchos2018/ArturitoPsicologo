package com.example.arturitopsicologo.Presenter;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.arturitopsicologo.Interface.InterfaceLectura;
import com.example.arturitopsicologo.Model.Lectura;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class PresenterCategoria {

    private Context mContext;
    private DatabaseReference databaseReference;
    private  String categoria_id;

    //no uso estowe
    public PresenterCategoria(Context mContext, DatabaseReference databaseReference, String categoria_id) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.categoria_id=categoria_id;

    }

    public void ViewCategoria(InterfaceLectura interfaceCita, String CategoriaId, String id) {
        databaseReference.child("Lecturas").child(CategoriaId).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Lectura obj = snapshot.getValue(Lectura.class);
                    interfaceCita.onCallback(obj);
                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
