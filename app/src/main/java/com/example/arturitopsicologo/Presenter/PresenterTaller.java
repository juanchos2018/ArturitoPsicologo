package com.example.arturitopsicologo.Presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arturitopsicologo.Adapter.AdapterAtencion;
import com.example.arturitopsicologo.Adapter.AdapterLecturaDos;
import com.example.arturitopsicologo.Adapter.AdapterMemoria;
import com.example.arturitopsicologo.Interface.InterfaceObjeto;
import com.example.arturitopsicologo.Interface.InterfaceTaller;
import com.example.arturitopsicologo.Model.Atencion;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.Memoria;
import com.example.arturitopsicologo.Model.Resultado;
import com.example.arturitopsicologo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PresenterTaller {

    private Context mContext;
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private AdapterLecturaDos adapter;
    private AdapterAtencion adapter2;
    private AdapterMemoria adapter3;
    private  String paciente_id;

    android.app.AlertDialog.Builder builder;
    AlertDialog alert;

    AlertDialog alert1;
    private InterfaceTaller interfaceTaller;

    public PresenterTaller(Context mContext, DatabaseReference databaseReference, String paciente_id,InterfaceTaller interfaceTaller) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.paciente_id=paciente_id;
        this.interfaceTaller=interfaceTaller;
    }

    public  void cargarRecyclerLectura(RecyclerView recyclerView, String CategoriaId){
        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).addValueEventListener(new ValueEventListener() {
            ArrayList<Lectura> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Lectura model=item.getValue(Lectura.class);
                    lista.add(model);
                }
                adapter= new AdapterLecturaDos(lista, mContext);
                recyclerView.setAdapter(adapter);
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id =lista.get(recyclerView.getChildAdapterPosition(view)).getId();
                        ViewLectura(CategoriaId, id);


                    }
                });
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public  void cargarRecyclerAtencion(RecyclerView recyclerView, String CategoriaId){

        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).addValueEventListener(new ValueEventListener() {
            ArrayList<Atencion> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Atencion model=item.getValue(Atencion.class);
                    lista.add(model);
                }
                adapter2= new AdapterAtencion(lista, mContext);
                recyclerView.setAdapter(adapter2);
                adapter2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id=lista.get(recyclerView.getChildAdapterPosition(view)).getId();
                        ViewAtencion(CategoriaId,id);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public  void cargarRecyclerMemoria(RecyclerView recyclerView, String CategoriaId){
        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).addValueEventListener(new ValueEventListener() {
            ArrayList<Memoria> lista;
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lista=new ArrayList<>();
                for (DataSnapshot item:snapshot.getChildren()){
                    Memoria model=item.getValue(Memoria.class);
                    lista.add(model);
                }
                adapter3= new AdapterMemoria(lista, mContext);
                recyclerView.setAdapter(adapter3);
                adapter3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id=lista.get(recyclerView.getChildAdapterPosition(view)).getId();
                        ViewMemoria(CategoriaId,id);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void ViewLectura(String CategoriaId, String id) {
        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Lectura obj = snapshot.getValue(Lectura.class);
                   // DialogoLectura(obj,CategoriaId,id);
                    interfaceTaller.onCallbackLectura(obj);
                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ViewAtencion(String CategoriaId, String id) {
        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Atencion obj = snapshot.getValue(Atencion.class);
                    interfaceTaller.onCallbackAtencion(obj);
                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ViewMemoria(String CategoriaId, String id) {
        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Memoria obj = snapshot.getValue(Memoria.class);
                  //  DialogoMemoria(obj);
                    interfaceTaller.onCallbackMemoria(obj);
                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void  DialogoLectura(Lectura obj,String CategoriaId,String id){

        builder = new AlertDialog.Builder(mContext);

        Button btcerrrar;
        CheckBox che1,che2,che3;
        TextView etLectura,pregunta1,pregunta2,pregunta3,tvrespuesta1,tvrespuesta2,tvrespuesta3;
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialogo_result_lectura, null);
        btcerrrar=(Button)v.findViewById(R.id.btnguardar);
        etLectura=(TextView)v.findViewById(R.id.etLectura);
        pregunta1=(TextView)v.findViewById(R.id.pregunta1);
        pregunta2=(TextView)v.findViewById(R.id.pregunta2);
        pregunta3=(TextView)v.findViewById(R.id.pregunta3);
        tvrespuesta1=(TextView)v.findViewById(R.id.tvrespuesta1);
        tvrespuesta2=(TextView)v.findViewById(R.id.tvrespuesta2);
        tvrespuesta3=(TextView)v.findViewById(R.id.tvrespuesta3);

        etLectura.setText(obj.getLectura());
        pregunta1.setText(obj.getPregunta1());
        pregunta2.setText(obj.getPregunta2());
        pregunta3.setText(obj.getPregunta3());

        tvrespuesta1.setText(obj.getRespuesta1());
        tvrespuesta2.setText(obj.getRespuesta2());
        tvrespuesta3.setText(obj.getRespuesta3());

        che1=(CheckBox)v.findViewById(R.id.che1);
        che2=(CheckBox)v.findViewById(R.id.che2);
        che3=(CheckBox)v.findViewById(R.id.che3);

        builder.setView(v);

        btcerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  puntosTotal=0;
                String chec1,chec2,chec3;
                if (che1.isChecked()){
                    chec1="true";
                    obj.setPuntores1("1");
                    puntosTotal=puntosTotal+1;
                }else {
                    chec1="false";
                    obj.setPuntores1("0");
                }
                if (che2.isChecked()){
                    chec2="true";
                    puntosTotal=puntosTotal+1;
                    obj.setPuntores2("1");
                }else {
                    chec2="false";
                    obj.setPuntores2("0");
                }
                if (che3.isChecked()){
                    chec3="true";
                    puntosTotal=puntosTotal+1;
                    obj.setPuntores3("1");
                }else {
                    chec3="false";
                    obj.setPuntores3("0");
                }
               // alert.dismiss();
                Resultado resultado = new Resultado();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                String fecha_id = sdf.format(new Date());
                resultado.setFecha_id(fecha_id);
                resultado.setPuntoLectura(String.valueOf(puntosTotal));
                resultado.setPuntoMemoria("");
                resultado.setPuntoAtencion("");
                resultado.setFecha(fecha_id);
                alert.dismiss();
                resultado.setPaciente_id(paciente_id);
                String tipoprueba="Lectura";

             //   viewResult(fecha_id,resultado,tipoprueba);
                //saveResultLectura(resultado,fecha_id);
                //storepuntoLectura(id,CategoriaId,obj);

               // Toast.makeText(mContext, chec1+" "+chec2+" "+chec3, Toast.LENGTH_SHORT).show();
            }
        });
        alert  = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }

    public void storepuntoLectura(String id,String CategoriaId,Lectura lectura){

        progressDialog= new ProgressDialog(mContext);
        progressDialog.setMessage("Cargando..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Map<String,Object> obj= new HashMap<>();
        obj.put("corregido","si");
        obj.put("puntores1",lectura.getPuntores1());
        obj.put("puntores2",lectura.getPuntores2());
        obj.put("puntores3",lectura.getPuntores3());

        databaseReference.child("TallerPaciente").child(paciente_id).child(CategoriaId).child(id).updateChildren(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
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

    public  void saveResultLectura(Object resultado, String fecha_id){

        databaseReference.child("Resultado").child(paciente_id).child(fecha_id).setValue(resultado).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                   // progressDialog.dismiss();
                    // Toast.makeText(mContext, "Registrado", Toast.LENGTH_SHORT).show();
                     DialogOk("Registrado");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(mContext, "erroorrrr  "+e.getMessage(), Toast.LENGTH_SHORT).show();
               // progressDialog.dismiss();
            }
        });
    }


    public void  viewResult(InterfaceObjeto interfaceCita, String fecha_id, String tipoprueba){

        databaseReference.child("Resultado").child(paciente_id).child(fecha_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                Resultado  obj = snapshot.getValue(Resultado.class);
                if (snapshot.exists()){
                    //Toast.makeText(mContext, "Existe", Toast.LENGTH_SHORT).show();

                    if (tipoprueba.equals("Lectura")){
                       // obj.setPuntoLectura(resultado.getPuntoLectura());
                        //obj.setFecha_id(fecha_id);
                       // updateResult(obj);
                        interfaceCita.onCallback(obj);
                       // Toast.makeText(mContext, "existe"+obj.getPuntoLectura(), Toast.LENGTH_SHORT).show();
                    }
                    else if (tipoprueba.equals("Atencion")){
                       //  obj.setPuntoAtencion(resultado.getPuntoAtencion());
                       // obj.setFecha_id(fecha_id);
                        interfaceCita.onCallback(obj);
                       // updateResult(obj);
                    }
                    else if (tipoprueba.equals("Memoria")){
                       //   obj.setPuntoMemoria(resultado.getPuntoMemoria());
                       // obj.setFecha_id(fecha_id);
                        interfaceCita.onCallback(obj);
                       // updateResult(obj);
                    }
                }else{

                  //  resultado.setPuntoMemoria("no existe  we ");
                    interfaceCita.onCallback(obj);

                  //  Toast.makeText(mContext, "No existe", Toast.LENGTH_SHORT).show();
                   // saveResultLectura(resultado,fecha_id);
                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public  void updateResult(Resultado resultado){

        Map<String,Object> obj= new HashMap<>();
        obj.put("puntoAtencion",resultado.getPuntoAtencion());
        obj.put("puntoLectura",resultado.getPuntoLectura());
        obj.put("puntoMemoria",resultado.getPuntoMemoria());

        databaseReference.child("Resultado").child(paciente_id).child(resultado.getFecha_id()).updateChildren(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    databaseReference.onDisconnect();
                   // progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(mContext, "err "+e.getMessage(), Toast.LENGTH_SHORT).show();
             //   progressDialog.dismiss();
            }
        });
    }
    public   void  updateAtencion(Atencion atencion){

        progressDialog= new ProgressDialog(mContext);
        progressDialog.setMessage("Cargando..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Map<String,Object> obj= new HashMap<>();
        obj.put("categoriaId",atencion.getCategoriaId());
        obj.put("id",atencion.getId());
        obj.put("puntaje",atencion.getPuntaje());

        databaseReference.child("TallerPaciente").child(paciente_id).child(atencion.getCategoriaId()).child(atencion.getId()).updateChildren(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();

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

    private void DialogoMemoria(Memoria obj) {


    }

    private void  DialogOk(String mensaje){
        android.app.AlertDialog.Builder builder1;

        builder1 = new AlertDialog.Builder(mContext);
        Button btcerrrar;
        TextView tvestado;
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialogo_ok, null);
        btcerrrar=(Button)v.findViewById(R.id.idbtncerrardialogo);
        tvestado=(TextView)v.findViewById(R.id.idestado);
        tvestado.setText(mensaje);
        builder1.setView(v);
        btcerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert1.dismiss();
            }
        });
        alert1  = builder1.create();
        alert1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert1.show();
    }
}
