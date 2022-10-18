package com.example.arturitopsicologo.Test;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StyleableRes;

import com.example.arturitopsicologo.Model.Fecha;
import com.example.arturitopsicologo.Model.Horario;
import com.example.arturitopsicologo.Model.Psicologo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class UsuarioTest {


    public  String login(String correo,String password){

        String result="";

        if (TextUtils.isEmpty(correo)) {
            result="fail";
        }
        else if (TextUtils.isEmpty(password)) {
            result="fail";
        }else{
            result="ok";
        }
        return  result;
    }

    public String editarPerfil(String psicologo_id,String nombres, String apellidos, String phone, String info, String formacion, String descripcion) {

        String result="";
        if (TextUtils.isEmpty(nombres)){
            result="fail";
        }
        else if (TextUtils.isEmpty(apellidos)){
            result="fail";
        }else if (TextUtils.isEmpty(phone)){
            result="fail";
        }
        else if (TextUtils.isEmpty(formacion)){
            result="fail";
        }
        else if (TextUtils.isEmpty(descripcion)){
            result="fail";
        }
        else if (TextUtils.isEmpty(info)){
            result="fail";
        }
        else {

            Map<String,Object> obj= new HashMap<>();
            obj.put("nombres",nombres);
            obj.put("apellido",apellidos);
            obj.put("info",info);
            obj.put("descripcion",descripcion);
            obj.put("formacion",formacion);
            obj.put("phone",phone);
            DatabaseReference databaseReference;
            databaseReference= FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Psicologos").child(psicologo_id).updateChildren(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){
                      //  result="ok";
                        Log.e("statrus","registrado");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Log.e("statrus","fail");
                }
            });
            result="ok";

        }
        return  result;
    }


    public String  AgregarFecha(String fecha,String psicologo_id) {
        String result="";
        if (TextUtils.isEmpty(fecha)){
            result="fail";
        }
        else{
            DatabaseReference databaseReference;
            databaseReference= FirebaseDatabase.getInstance().getReference();
            String key =databaseReference.push().getKey();
            Fecha objfecha = new Fecha();
            objfecha.setId(key);
            objfecha.setFecha(fecha);
            objfecha.setUser_id(psicologo_id);
            databaseReference.child("Fechas").child(objfecha.getUser_id()).child(key).setValue(fecha).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {

                }
            });
            result="ok";
        }
        return  result;
    }

    public  String AgregarHora(String psicologo_id,String fecha_id ,String hora_incio, String hora_fin){

        String result="";
        if (TextUtils.isEmpty(hora_incio)){
            result="fail";
        }
        else if (TextUtils.isEmpty(hora_fin)){
            result="fail";
        }else{

            int index=1;
            DatabaseReference databaseReference;
            databaseReference= FirebaseDatabase.getInstance().getReference();
            String key =databaseReference.push().getKey();

            Horario horario = new Horario();
            horario.setId(key);
            horario.setHora_inicio(hora_incio);
            horario.setHora_fin(hora_fin);
            horario.setIndex((int) index+1);

            databaseReference.child("Fechas").child(psicologo_id).child(fecha_id).child("Horas").child(key).setValue(horario).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {

                }
            });

            result="ok";
        }

        return  result;
    }
    public  String EditarHora(String psicologo_id,String  id_hora,String fecha_id ,String hora_incio, String hora_fin){

        String result="";
        if (TextUtils.isEmpty(hora_incio)){
            result="fail";
        }
        else if (TextUtils.isEmpty(hora_fin)){
            result="fail";
        }else {
            DatabaseReference databaseReference;
            databaseReference= FirebaseDatabase.getInstance().getReference();
            Map<String,Object> obj= new HashMap<>();
            obj.put("hora_inicio",hora_incio);
            obj.put("hora_fin",hora_fin);

            databaseReference.child("Fechas").child(psicologo_id).child(fecha_id).child("Horas").child(id_hora).updateChildren(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()){

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                }
            });
            result="ok";

        }

        return  result;
    }




}
