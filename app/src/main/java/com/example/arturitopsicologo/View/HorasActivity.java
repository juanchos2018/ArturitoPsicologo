package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.arturitopsicologo.Interface.InterfaceFecha;
import com.example.arturitopsicologo.Interface.InterfaceHorario;
import com.example.arturitopsicologo.Model.Horario;
import com.example.arturitopsicologo.Presenter.PresenterFecha;
import com.example.arturitopsicologo.Presenter.PresenterHorario;
import com.example.arturitopsicologo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HorasActivity extends AppCompatActivity  implements View.OnClickListener, InterfaceHorario {

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
    String turno="";

    ImageView imgfinish;
     int horaInicio=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horas);

        reference= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        fecha_id=getIntent().getStringExtra("fecha_id");

        presenter= new PresenterHorario(this,reference,user_id,fecha_id,this);
        inputs();
    }

    private void inputs() {
        btnaddHora=(FloatingActionButton) findViewById(R.id.btnaddHoras);
        btnaddHora.setOnClickListener(this);
        imgfinish=(ImageView) findViewById(R.id.imgfinish);
        imgfinish.setOnClickListener(this);
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
              //  timer();
                break;
            case R.id.imgfinish:
                finishs();
                break;
        }
    }

    private  void  finishs(){
        finish();
    }

    private void  DialoHoras(){
        builder = new AlertDialog.Builder(this);
        Button btguardar,btnhora1,btnhora2;
        View v = LayoutInflater.from(this).inflate(R.layout.dialogo_horas, null);
        btguardar=(Button)v.findViewById(R.id.btngurdarhora);
        btnhora1=(Button)v.findViewById(R.id.btnhora1);
        btnhora2=(Button)v.findViewById(R.id.btnhora2);


        btguardar.setEnabled(false);
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
                        horaInicio=hourOfDay;
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
                        if (hourOfDay<=horaInicio) {
                            Toast.makeText(HorasActivity.this, "No puedes elegir iguala la hora inicio", Toast.LENGTH_SHORT).show();
                        }else{
                            btguardar.setEnabled(true);
                            if(hourOfDay < 12) {
                                AM_PM = "a.m.";
                                turno="mañana";
                            } else {
                                AM_PM = "p.m.";
                                turno="tarde";
                            }
                            btnhora2.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
                        }
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
                store("",hora_incio,hora_fin);
                alert.dismiss();
            }
        });
        alert  = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }

    private void  DialoHoras2(String id,String hora1,String hora2){
        builder = new AlertDialog.Builder(this);
        Button btguardar,btnhora1,btnhora2;
        View v = LayoutInflater.from(this).inflate(R.layout.dialogo_horas, null);
        btguardar=(Button)v.findViewById(R.id.btngurdarhora);
        btnhora1=(Button)v.findViewById(R.id.btnhora1);
        btnhora2=(Button)v.findViewById(R.id.btnhora2);

        builder.setView(v);
        btnhora1.setText(hora1);
        btnhora2.setText(hora2);

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
                            turno="mañana";
                        } else {
                            AM_PM = "p.m.";
                            turno="tarde";
                        }
                        btnhora2.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
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
                store(id,hora_incio,hora_fin);
                alert.dismiss();
            }
        });
        alert  = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }

    private  void  timer(){
        DatePickerDialog picker = new DatePickerDialog(
                this,
                null, // instead of a listener
                2012, 6, 15);
        picker.setCancelable(true);
        picker.setCanceledOnTouchOutside(true);
        picker.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(HorasActivity.this, "positive", Toast.LENGTH_SHORT).show();
                    }
                });
        picker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(HorasActivity.this, "negative", Toast.LENGTH_SHORT).show();
                    }
                });
        picker.show();
    }


    private void  store(String id,String hora_incio, String hora_fin){
        if (TextUtils.isEmpty(hora_incio)){
            Toast.makeText(this, "Seleccione la hora", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(hora_fin)){
            Toast.makeText(this, "Seleccione hora fin", Toast.LENGTH_SHORT).show();
        }else{
            Horario obj = new Horario();
            obj.setId(id);
            obj.setHora_inicio(hora_incio);
            obj.setHora_fin(hora_fin);
            obj.setEstado("1");
            obj.setTurno(turno);
            presenter.store(obj);
            Toast.makeText(this, "trurbi" +turno, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCallback(Horario value) {
        DialoHoras2(value.getId(),value.getHora_inicio(),value.getHora_fin());
    }

    @Override
    public Context getContext() {
        return this;
    }
}