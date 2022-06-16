package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.arturitopsicologo.Interface.InterfaceFecha;
import com.example.arturitopsicologo.Model.Fecha;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Presenter.PresenterFecha;
import com.example.arturitopsicologo.Presenter.PresenterLectura;
import com.example.arturitopsicologo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HorarioActivity extends AppCompatActivity  implements View.OnClickListener, InterfaceFecha {



    FloatingActionButton btnaddFecha;

    public final Calendar c = Calendar.getInstance();
    //Variables para obtener la fecha
    private static final String CERO = "0";
    private static final String BARRA = "-";
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    final int hora = 07;
    final int minuto = 0;
    private static final String DOS_PUNTOS = ":";
    Locale idlocalte = new Locale("in", "ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", idlocalte);
    Date tgl_daftar_date;
    String user_id;

    PresenterFecha presenter;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        reference= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        presenter= new PresenterFecha(this,reference,this,user_id);
        inputs();
    }

    @Override
    protected void onStart() {
        super.onStart();
        records();
    }

    private void records() {

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recylerFechas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecycler(recyclerView,user_id);
    }

    private void inputs() {
        btnaddFecha=(FloatingActionButton) findViewById(R.id.btnaddFecha);

        btnaddFecha.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.btnaddFecha:
                DialoFecha("");
                break;
        }
    }
    private  void  DialoFecha(String id){

        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                c.set(year, month, dayOfMonth);
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //etfecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                tgl_daftar_date = c.getTime();
                String fecha =diaFormateado + BARRA + mesFormateado + BARRA + year;
                Toast.makeText(HorarioActivity.this, fecha, Toast.LENGTH_SHORT).show();
                store(fecha,tgl_daftar_date.toString(),id);
            }
        },anio, mes, dia);
        recogerFecha.show();
    }
    private void store(String fecha, String fecha1,String id) {

        if (TextUtils.isEmpty(fecha)){
            Toast.makeText(this, "fecha ncesaria", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(fecha1)){
            Toast.makeText(this, "fecha ncesaria", Toast.LENGTH_SHORT).show();
        }
        else{
            Fecha obj = new Fecha();
            obj.setId(id);
            obj.setUser_id(user_id);
            obj.setFecha(fecha);
            obj.setFecha1(fecha1);
            obj.setEstado("1");
            presenter.store(obj);
        }
    }

    @Override
    public void onCallback(Fecha value) {
        DialoFecha(value.getId());
    }

    @Override
    public Context getContext() {
        return this;
    }
}