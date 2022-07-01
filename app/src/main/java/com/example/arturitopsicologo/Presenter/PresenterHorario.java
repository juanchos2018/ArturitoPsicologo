package com.example.arturitopsicologo.Presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arturitopsicologo.Adapter.AdapterFechas;
import com.example.arturitopsicologo.Adapter.AdapterHoras;
import com.example.arturitopsicologo.Interface.InterfaceClick;
import com.example.arturitopsicologo.Interface.InterfaceFecha;
import com.example.arturitopsicologo.Interface.InterfaceHorario;
import com.example.arturitopsicologo.Model.Fecha;
import com.example.arturitopsicologo.Model.Horario;
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
import java.util.HashMap;
import java.util.Map;

public class PresenterHorario {

    private Context mContext;
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private AdapterHoras adapter;
    android.app.AlertDialog.Builder builder;
    AlertDialog alert;
    String user_id,fecha_id;
    long index=0;
    private InterfaceHorario interfaceHorario;

    public PresenterHorario(Context mContext, DatabaseReference databaseReference, String user_id,String fecha_id, InterfaceHorario interfaceHorario) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.user_id=user_id;
        this.fecha_id=fecha_id;
        this.interfaceHorario=interfaceHorario;
    }

    private  void save(Horario horario){

        CantidaItems();
        String key =databaseReference.push().getKey();
       // String key =dbReference.push().getKey();
        horario.setId(key);
        horario.setIndex((int) index+1);

        databaseReference.child("Fechas").child(user_id).child(fecha_id).child("Horas").child(key).setValue(horario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    DialogOk("Registrado");
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

    public  void store(Horario horas){
        progressDialog= new ProgressDialog(mContext);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (TextUtils.isEmpty(horas.getId())){
            save(horas);
        }else{
            update(horas);
        }
    }

    public  void cargarRecycler(RecyclerView recyclerView){
        databaseReference.child("Fechas").child(user_id).child(fecha_id).child("Horas").addValueEventListener(new ValueEventListener() {
            ArrayList<Horario> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Horario model=item.getValue(Horario.class);
                    lista.add(model);
                }
                adapter= new AdapterHoras(lista, mContext, new InterfaceClick() {
                    @Override
                    public void onCallback(String value) {
                        ViewHorario(value);
                    }
                });
                recyclerView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private  void  update(Horario fecha){

        Map<String,Object> obj= new HashMap<>();
        obj.put("hora_inicio",fecha.getHora_inicio());
        obj.put("hora_inicio",fecha.getHora_inicio());

        databaseReference.child("Fechas").child(user_id).child(fecha_id).child("Horas").child(fecha.getId()).updateChildren(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    DialogOk("Editado");
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

    private void  CantidaItems(){
        databaseReference.child("Fechas").child(user_id).child(fecha_id).child("Horas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    index=(snapshot.getChildrenCount());
                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ViewHorario(String id) {
        databaseReference.child("Fechas").child(user_id).child(fecha_id).child("Horas").child(id).addValueEventListener(new ValueEventListener(){
        //databaseReference.child("Fechas").child(user_id).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Horario obj = snapshot.getValue(Horario.class);
                    interfaceHorario.onCallback(obj);
                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
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
