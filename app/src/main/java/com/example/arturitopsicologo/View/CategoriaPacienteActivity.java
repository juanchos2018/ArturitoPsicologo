package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.arturitopsicologo.Fragment.AtencionFragment;
import com.example.arturitopsicologo.Fragment.BottonSheetFragment;
import com.example.arturitopsicologo.Fragment.LecturaFragment;
import com.example.arturitopsicologo.Fragment.MemoriaFragment;
import com.example.arturitopsicologo.Interface.InterfacePaciente;
import com.example.arturitopsicologo.Interface.InterfaceTaller;
import com.example.arturitopsicologo.Model.Atencion;
import com.example.arturitopsicologo.Model.Historial;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.Memoria;
import com.example.arturitopsicologo.Model.Paciente;
import com.example.arturitopsicologo.Presenter.PresenterPaciente;
import com.example.arturitopsicologo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CategoriaPacienteActivity extends AppCompatActivity implements LecturaFragment.OnFragmentInteractionListener,AtencionFragment.OnFragmentInteractionListener,MemoriaFragment.OnFragmentInteractionListener , InterfaceTaller {

    LecturaFragment fragment1;
    AtencionFragment fragment2;
    MemoriaFragment fragment3;
    String paciente_id;
    Button btn1,btn2,btn3;
    ImageView imgfinish,imghistorial;
    private DatabaseReference reference;
    PresenterPaciente presenterPaciente;

    TextView tvnombres,tvapellidos,tvedad;
    Button btnver;

    android.app.AlertDialog.Builder builder;
    AlertDialog alert;
    ImageView imgpaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_paciente);

        inputs();
        paciente_id=getIntent().getStringExtra("paciente_id");

        fragment1=new LecturaFragment(paciente_id);
        fragment2=new AtencionFragment(paciente_id);
        fragment3=new MemoriaFragment(paciente_id);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment1).commit();

        btn1=(Button) findViewById(R.id.btn1);
        btn2=(Button) findViewById(R.id.btn2);
        btn3=(Button) findViewById(R.id.btn3);
        imgpaciente=(ImageView) findViewById(R.id.imgpaciente);

        reference= FirebaseDatabase.getInstance().getReference();
        btnver=(Button)findViewById(R.id.btnver);

        btnver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resutladis();

            }
        });

        presenterPaciente= new PresenterPaciente(this,reference,"","","","");
    }

    private void inputs() {
        tvnombres=(TextView) findViewById(R.id.tvnombres);
        tvapellidos=(TextView) findViewById(R.id.tvapellidos);
        tvedad=(TextView) findViewById(R.id.tvedad);
        imghistorial=(ImageView) findViewById(R.id.imghistorial);
        imghistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialoghistorial();
            }
        });
    }
    private void resutladis(){
        BottonSheetFragment bottomSheetDialog = BottonSheetFragment.newInstance();
        bottomSheetDialog.paciente_id=paciente_id;
        bottomSheetDialog.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!TextUtils.isEmpty(paciente_id)){
            ViewPaciente(paciente_id);
        }
    }

    private void ViewPaciente(String paciente_id) {

        presenterPaciente.infoPaciente(new InterfacePaciente() {
            @Override
            public void onCallback(Paciente value) {
                tvnombres.setText(value.getNombre());
                tvapellidos.setText(value.getApellidos());
                tvedad.setText("Edad : "+value.getEdad());


                if (value.getPhoto().equals("default")){
                   imgpaciente.setImageResource(R.drawable.default_profile_image);
                }else{
                    Picasso.get().load(value.getPhoto()).fit().centerCrop().into(imgpaciente);
                }

            }
        },paciente_id);
    }

    public void onClick(View view) {

        FragmentTransaction Transaction=getSupportFragmentManager().beginTransaction();

        switch (view.getId()){
            case R.id.btn1:
                btn1.setBackgroundColor(getResources().getColor(R.color.crema));
                btn2.setBackgroundColor(getResources().getColor(R.color.buttonLogin));
                btn3.setBackgroundColor(getResources().getColor(R.color.buttonLogin));
                Transaction.replace(R.id.main_container,fragment1);
                break;
            case R.id.btn2:
                btn1.setBackgroundColor(getResources().getColor(R.color.buttonLogin));
                btn2.setBackgroundColor(getResources().getColor(R.color.crema));
                btn3.setBackgroundColor(getResources().getColor(R.color.buttonLogin));
                Transaction.replace(R.id.main_container,fragment2);
                break;
            case R.id.btn3:
                btn1.setBackgroundColor(getResources().getColor(R.color.buttonLogin));
                btn2.setBackgroundColor(getResources().getColor(R.color.buttonLogin));
                btn3.setBackgroundColor(getResources().getColor(R.color.crema));
                Transaction.replace(R.id.main_container,fragment3);
                break;


        }
        Transaction.commit();
    }

    private void dialoghistorial() {
        builder = new AlertDialog.Builder(this);
        Button btguardar,btnhora1,btnhora2;
        EditText tvtitulo,tvdescripcion;

        View v = LayoutInflater.from(this).inflate(R.layout.dialogo_historial, null);
        btguardar=(Button)v.findViewById(R.id.btnguardar);
        tvtitulo=(EditText)v.findViewById(R.id.tvtitulo);
        tvdescripcion=(EditText)v.findViewById(R.id.tvdescripcion);
        builder.setView(v);


        btguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo=tvtitulo.getText().toString();
                String descripci=tvdescripcion.getText().toString();
                save(titulo,descripci);
                alert.dismiss();
            }
        });
        alert  = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();


    }

    private  void  save(String titulo, String descripci){

        Historial historial = new Historial();
        historial.setCategoria("categoria");
        historial.setTitulo(titulo);
        historial.setDescripcion(descripci);
        historial.setPaciente_id(paciente_id);
        historial.setFecha("fecha");
        presenterPaciente.saveHistorial(historial);

    }


    private  void  finishs(){
        finish();
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCallbackLectura(Lectura value) {

    }

    @Override
    public void onCallbackAtencion(Atencion value) {

    }

    @Override
    public void onCallbackMemoria(Memoria value) {

    }

    @Override
    public Context getContext() {
        return null;
    }
}