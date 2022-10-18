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

import com.example.arturitopsicologo.Adapter.AdapterLectura;
import com.example.arturitopsicologo.Adapter.AdapterLenguaje;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.Lenguaje;
import com.example.arturitopsicologo.Model.PsicoloPaciente;
import com.example.arturitopsicologo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class PresenterLenguaje {


    private Context mContext;
    private DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    private AdapterLenguaje adapter;
    android.app.AlertDialog.Builder builder;
    AlertDialog alert;
    String user_id;
    String CategoriaId="";


    ArrayList<Lenguaje> listaLenguajes;


    public PresenterLenguaje(Context mContext, DatabaseReference databaseReference, String user_id,String CategoriaId) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.user_id=user_id;
        this.CategoriaId=CategoriaId;
    }

    public  void store(Lenguaje lenguaje){

        if (TextUtils.isEmpty(lenguaje.getId())){
            save(lenguaje);
        }else{
            //update(lectura);
        }
    }


    private  void save(Lenguaje lenguaje){
        String key =databaseReference.push().getKey();
        lenguaje.setId(key);
        databaseReference.child("Lenguajes").child(user_id).child(lenguaje.getCategoriaId()).child(key).setValue(lenguaje).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()){
                   // progressDialog.dismiss();
                    DialogOk("Registrado");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(mContext, "err "+e.getMessage(), Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
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
