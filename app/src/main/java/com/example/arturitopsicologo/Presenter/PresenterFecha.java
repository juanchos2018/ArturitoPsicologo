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
import com.example.arturitopsicologo.Adapter.AdapterLectura;
import com.example.arturitopsicologo.Interface.InterfaceFecha;
import com.example.arturitopsicologo.Interface.InterfaceId;
import com.example.arturitopsicologo.Interface.InterfaceLectura;
import com.example.arturitopsicologo.Model.Fecha;
import com.example.arturitopsicologo.Model.Lectura;
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

public class PresenterFecha  {

    private Context mContext;
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private AdapterFechas adapter;
    android.app.AlertDialog.Builder builder;
    AlertDialog alert;

    private InterfaceFecha interfaceFecha;
    String user_id;

    public PresenterFecha(Context mContext, DatabaseReference databaseReference,InterfaceFecha interfaceFecha,String user_id) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.interfaceFecha = interfaceFecha;
        this.user_id=user_id;
    }


    public  void cargarRecycler(RecyclerView recyclerView, String user_id){
        databaseReference.child("Fechas").child(user_id).addValueEventListener(new ValueEventListener() {
            ArrayList<Fecha> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Fecha model=item.getValue(Fecha.class);
                    lista.add(model);
                }
                adapter= new AdapterFechas(lista, mContext, new InterfaceId() {
                    @Override
                    public void getId(String id) {
                        Toast.makeText(mContext, id, Toast.LENGTH_SHORT).show();
                        ViewFecha(id);
                    }
                });
                recyclerView.setAdapter(adapter);
//                adapter.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void ViewFecha(String id) {
        databaseReference.child("Fechas").child(user_id).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Fecha obj = snapshot.getValue(Fecha.class);
                    interfaceFecha.onCallback(obj);
                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void store(Fecha fecha){
        progressDialog= new ProgressDialog(mContext);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (TextUtils.isEmpty(fecha.getId())){
            save(fecha);
        }else{
         //   update(lectura);
        }
    }
    private  void save(Fecha fecha){
        String key =databaseReference.push().getKey();
        fecha.setId(key);
        databaseReference.child("Fechas").child(fecha.getUser_id()).child(key).setValue(fecha).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    private  void  update(Lectura lectura){

        Map<String,Object> obj= new HashMap<>();
        obj.put("titulo",lectura.getTitulo());
        obj.put("categoriaId",lectura.getCategoriaId());
        obj.put("id",lectura.getId());
        obj.put("lectura",lectura.getLectura());
        obj.put("pregunta1",lectura.getPregunta1());
        obj.put("pregunta2",lectura.getPregunta2());
        obj.put("pregunta3",lectura.getPregunta3());

        databaseReference.child("Lecturas").child(lectura.getCategoriaId()).child(lectura.getId()).updateChildren(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
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
