package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.arturitopsicologo.Model.Horario;
import com.example.arturitopsicologo.Presenter.PresenterFecha;
import com.example.arturitopsicologo.Presenter.PresenterHorario;
import com.example.arturitopsicologo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HorasActivity extends AppCompatActivity  implements View.OnClickListener {



    FloatingActionButton btnaddHora;

    PresenterHorario presenter;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    final int hora = 07;
    final int minuto = 0;
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    android.app.AlertDialog.Builder builder;
    AlertDialog alert;
    String user_id;
    String fecha_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horas);

        reference= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        fecha_id=getIntent().getStringExtra("fecha_id");

        presenter= new PresenterHorario(this,reference,user_id,fecha_id);
        inputs();

    }

    private void inputs() {
        btnaddHora=(FloatingActionButton) findViewById(R.id.btnaddHoras);
        btnaddHora.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        records();
    }

    private void records() {

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recylerHoras);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecycler(recyclerView);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnaddHoras:
                DialoHoras();
                break;
        }

    }

    private void  DialoHoras(){
        builder = new AlertDialog.Builder(this);
        Button btguardar,btnhora1,btnhora2;

        View v = LayoutInflater.from(this).inflate(R.layout.dialogo_horas, null);
        btguardar=(Button)v.findViewById(R.id.btngurdarhora);
        btnhora1=(Button)v.findViewById(R.id.btnhora1);
        btnhora2=(Button)v.findViewById(R.id.btnhora2);

        builder.setView(v);

        btnhora1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog recogerHora = new TimePickerDialog(HorasActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                        String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                        String AM_PM;
                        if(hourOfDay < 12) {
                            AM_PM = "a.m.";
                        } else {
                            AM_PM = "p.m.";
                        }
                        btnhora1.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
                        // ethora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
                    }
                }, hora, minuto, false);
                recogerHora.show();
            }
        });
        btnhora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog recogerHora = new TimePickerDialog(HorasActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                        String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                        String AM_PM;
                        if(hourOfDay < 12) {
                            AM_PM = "a.m.";
                        } else {
                            AM_PM = "p.m.";
                        }
                        btnhora2.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
                        // ethora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
                    }
                }, hora, minuto, false);
                recogerHora.show();
            }
        });
        btguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hora_incio=btnhora1.getText().toString();
                String hora_fin=btnhora2.getText().toString();
                store(hora_incio,hora_fin);
                alert.dismiss();
            }
        });
        alert  = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }
    private void  store(String hora_incio, String hora_fin){
        if (TextUtils.isEmpty(hora_incio)){
            Toast.makeText(this, "Seleccione la ohra", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(hora_fin)){
            Toast.makeText(this, "SEelcciona hora fin", Toast.LENGTH_SHORT).show();
        }else{
            Horario obj = new Horario();
            obj.setId("");
            obj.setHora_inicio(hora_fin);
            obj.setHora_fin(hora_fin);
            obj.setEstado("1");
            presenter.store(obj);
        }
    }
}