package com.example.arturitopsicologo.Presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arturitopsicologo.Adapter.AdapterCitas;
import com.example.arturitopsicologo.Adapter.AdapterLectura;
import com.example.arturitopsicologo.Adapter.AdapterPsicologoPaciente;
import com.example.arturitopsicologo.Interface.InterfaceClick;
import com.example.arturitopsicologo.Interface.InterfacePaciente;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.Paciente;
import com.example.arturitopsicologo.Model.PsicoloPaciente;
import com.example.arturitopsicologo.R;
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


    android.app.AlertDialog.Builder builder;
    AlertDialog alert;




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

                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String paciente_id =lista.get(recyclerView.getChildAdapterPosition(view)).getPaciente_id();
                        infoPaciente(paciente_id);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    public void infoPaciente(String paciente_id) {

        databaseReference.child("Paciente").child(paciente_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Paciente userModel = snapshot.getValue(Paciente.class);
                    Dialogo(userModel);
                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(mContext, "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void  Dialogo(Paciente paciente){
        builder = new AlertDialog.Builder(mContext);
        Button btcerrrar;
        TextView tvnombrepaciente,tvapellidopaciente,tvapoderado;
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialogo_detalle, null);
        btcerrrar=(Button)v.findViewById(R.id.idbtncerrardialogo);
        tvnombrepaciente=(TextView)v.findViewById(R.id.tvnombrepaciente);
        tvapoderado=(TextView)v.findViewById(R.id.tvapoderado);
        tvapellidopaciente=(TextView)v.findViewById(R.id.tvapellidopaciente);
        tvapoderado=(TextView)v.findViewById(R.id.tvapoderado);
        tvnombrepaciente.setText(paciente.getNombre());
        tvapellidopaciente.setText(paciente.getApellidos());
        tvapoderado.setText(paciente.getContacto());

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
