package com.example.arturitopsicologo.View;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_NEUTRAL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
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

    ImageView imgfinish;


    DatePickerDialog recogerFecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        reference= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        presenter= new PresenterFecha(this,reference,this,user_id);
        Log.e("id_user",user_id);
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
        imgfinish=(ImageView) findViewById(R.id.imgfinish);
        imgfinish.setOnClickListener(this);
        btnaddFecha=(FloatingActionButton) findViewById(R.id.btnaddFecha);
        btnaddFecha.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.btnaddFecha:
                //DialoFecha("");
                DialogoFecha2("");
                break;
            case R.id.imgfinish:
                finishs();
                break;
        }
    }
    private  void  finishs(){
        finish();
    }
    private  void  DialoFecha(String id){

         recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                c.set(year, month, dayOfMonth);
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //etfecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                tgl_daftar_date = c.getTime();
                String fecha =diaFormateado + BARRA + mesFormateado + BARRA + year;
                store(fecha,tgl_daftar_date.toString(),id);
            }
        },anio, mes, dia);
        recogerFecha.show();
    }

    private  void  DialogoFecha(){
        DatePickerDialog picker = new DatePickerDialog(
                this,
                null, // instead of a listener
                anio, mes, dia);
        picker.setCancelable(true);
        picker.setCanceledOnTouchOutside(true);

        picker.setButton(DialogInterface.BUTTON_POSITIVE, "OK",

                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar newDate = Calendar.getInstance();
                        int dayOfMonth = picker.getDatePicker().getDayOfMonth();
                        int monthOfYear = picker.getDatePicker().getMonth() ;
                        int year = picker.getDatePicker().getYear();
                        Toast.makeText(HorarioActivity.this, "positive", Toast.LENGTH_SHORT).show();
                    }
                });
        picker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(HorarioActivity.this, "negative", Toast.LENGTH_SHORT).show();
                    }
                });
        picker.show();
    }
    private  void  DialogoFecha2(String id){

        Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog datepicker = new DatePickerDialog(this,null, newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        datepicker.setCancelable(true);
        datepicker.setCanceledOnTouchOutside(true);
        datepicker.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Calendar newDate = Calendar.getInstance();
                        int dayOfMonth = datepicker.getDatePicker().getDayOfMonth();
                        int monthOfYear = datepicker.getDatePicker().getMonth() ;
                        int year = datepicker.getDatePicker().getYear();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        dialog.dismiss();
                        final int mesActual = monthOfYear + 1;
                        String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                        String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                        //etfecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
                        tgl_daftar_date = c.getTime();
                        String fecha =diaFormateado + BARRA + mesFormateado + BARRA + year;
                        store(fecha,tgl_daftar_date.toString(),id);


                        //Toast.makeText(HorarioActivity.this, fecha, Toast.LENGTH_SHORT).show();
                        //tv.setText(CommonDateFunction.format(newDate.getTime(), CommonDateFunction.FORMAT_DD_MMM_YYYY));
                    }
                });
        datepicker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // Log.d("Picker", "Cancel!");
                    }
                });
        datepicker.show();
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
        //DialoFecha(value.getId());
        DialogoFecha2(value.getId());
    }

    @Override
    public Context getContext() {
        return this;
    }
}