package com.example.arturitopsicologo.Presenter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.arturitopsicologo.Adapter.AdapterLectura;
import com.example.arturitopsicologo.Interface.InterfacePsicologo;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.Psicologo;
import com.example.arturitopsicologo.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class PresenterPsicologo {

    private Context mContext;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    ProgressDialog progressDialog;
    private AdapterLectura adapter;
    android.app.AlertDialog.Builder builder;
    AlertDialog alert;

    public PresenterPsicologo(Context mContext, DatabaseReference databaseReference,StorageReference storageReference) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.storageReference=storageReference;
        //mAuth = FirebaseAuth.getInstance();
    }


    public void info(InterfacePsicologo interfacePsicologo, String user_id) {

        databaseReference.child("Psicologos").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Psicologo userModel = snapshot.getValue(Psicologo.class);
                    interfacePsicologo.onCallback(userModel);
                }
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public   void  update(Psicologo psicologo){
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("cargando");
        progressDialog.show();
        Map<String,Object> obj= new HashMap<>();
        obj.put("nombres",psicologo.getNombres());
        obj.put("apellido",psicologo.getApellido());
        obj.put("info",psicologo.getInfo());
        obj.put("descripcion",psicologo.getDescripcion());
        obj.put("formacion",psicologo.getFormacion());
        obj.put("phone",psicologo.getPhone());

        databaseReference.child("Psicologos").child(psicologo.getId()).updateChildren(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
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


    public void saveImage(String user_id, Uri uriphoto){

        if (uriphoto!=null){
            progressDialog= new ProgressDialog(mContext);
            progressDialog.setMessage("Cargando..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StorageReference fotoref=storageReference.child("fotos").child(uriphoto.getLastPathSegment());
            fotoref.putFile(uriphoto).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull @org.jetbrains.annotations.NotNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw  new Exception();
                    }
                    return fotoref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri uridownload =task.getResult();

                        Map<String,Object> obj= new HashMap<>();
                        obj.put("photo",uridownload.toString());

                        databaseReference.child("Psicologos").child(user_id).updateChildren(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(mContext, "Actualizado foto", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                                Toast.makeText(mContext, "err "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
                    }
                }
            });
        }else{
            Toast.makeText(mContext, "No selecciono Imagen ", Toast.LENGTH_SHORT).show();
        }
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
