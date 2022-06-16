package com.example.arturitopsicologo.Presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.arturitopsicologo.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PresenterRegister {

    private Context mContext;
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;

    public PresenterRegister(Context mContext, DatabaseReference databaseReference) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        mAuth = FirebaseAuth.getInstance();
        // user = mAuth.getCurrentUser();
    }

    public  void  register(String nombre, String apellido, String correo, String clave){

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("cargando");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(correo, clave)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String current_userID =  mAuth.getCurrentUser().getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Psicologos").child(current_userID);
                            databaseReference.child("nombres").setValue(nombre);
                            databaseReference.child("apellido").setValue(apellido);
                            databaseReference.child("photo").setValue("default");
                            databaseReference.child("info").setValue("");
                            databaseReference.child("descripcion").setValue("");
                            databaseReference.child("formacion").setValue("");
                            databaseReference.child("type").setValue("psicologo");
                            databaseReference.child("phone").setValue("");
                            databaseReference.child("correo").setValue(correo);
                            databaseReference.child("id").setValue(current_userID)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                // SENDING VERIFICATION EMAIL TO THE REGISTERED USER'S EMAIL
                                                user = mAuth.getCurrentUser();
                                                if (user != null){
                                                    user.sendEmailVerification()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()){
                                                                        progressDialog.dismiss();
                                                                        mAuth.signOut();
                                                                        Toast.makeText(mContext, "Registrado", Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        mAuth.signOut();
                                                                        progressDialog.dismiss();
                                                                    }
                                                                }
                                                            });
                                                }

                                            }
                                        }
                                    });

                        } else {
                            String message = task.getException().getMessage();
                            progressDialog.dismiss();
                            Toast.makeText(mContext, "Error occurred : " + message, Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    public  void  login(String correo,String clave){

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("cargando");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(correo,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    String userUID = mAuth.getCurrentUser().getUid();
                    //checkVerifiedEmail();
                    Intent intent = new Intent(mContext, MenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    mContext.startActivity(intent);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "verifique sus datos", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

}
