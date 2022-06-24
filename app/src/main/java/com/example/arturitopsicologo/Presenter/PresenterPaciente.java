package com.example.arturitopsicologo.Presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arturitopsicologo.Adapter.AdapterLectura;
import com.example.arturitopsicologo.Adapter.AdapterPsicologoPaciente;
import com.example.arturitopsicologo.Interface.InterfaceClick;
import com.example.arturitopsicologo.Interface.InterfaceLectura;
import com.example.arturitopsicologo.Model.Categoria;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.PsicoloPaciente;
import com.example.arturitopsicologo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class PresenterPaciente {

    private Context mContext;
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private AdapterPsicologoPaciente adapter;
    android.app.AlertDialog.Builder builder;
    AlertDialog alert;
    String psicologo_id;
    String CategoriaId;
    Lectura lectura;
    Categoria categoria;
    Object object;
    String id;

    public PresenterPaciente(Context mContext, DatabaseReference databaseReference,String psicologo_id,String CategoriaId, Object object,String id) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.psicologo_id=psicologo_id;
        this.CategoriaId=CategoriaId;
       // this.lectura=lectura;
        this.object=object;
        this.id=id;
    }

    public  void cargarRecycler(RecyclerView recyclerView,String CategoriaId){
        databaseReference.child("PsicologoPaciente").child(psicologo_id).addValueEventListener(new ValueEventListener() {
            ArrayList<PsicoloPaciente> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    PsicoloPaciente model=item.getValue(PsicoloPaciente.class);
                    lista.add(model);
                }
                adapter= new AdapterPsicologoPaciente(lista, mContext, new InterfaceClick() {
                    @Override
                    public void onCallback(String value) {
                        saveTaller(value);
                    }
                });
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private  void saveTaller(String paciente_id){
        progressDialog= new ProgressDialog(mContext);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).child(id).setValue(object).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    DialogOk("Enviado a Paciente");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(mContext, "err "+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    private void  DialogOk(String mensaje){
        builder = new AlertDialog.Builder(mContext);
        Button btcerrrar;
        TextView tvestado;
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialogo_ok, null);
        btcerrrar=(Button)v.findViewById(R.id.idbtncerrardialogo);
        tvestado=(TextView)v.findViewById(R.id.idestado);
        tvestado.setText(mensaje);
        builder.setView(v);
        btcerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert  = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }

}
