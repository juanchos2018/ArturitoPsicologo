package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arturitopsicologo.Fragment.AtencionFragment;
import com.example.arturitopsicologo.Fragment.LecturaFragment;
import com.example.arturitopsicologo.Fragment.MemoriaFragment;
import com.example.arturitopsicologo.Interface.InterfacePaciente;
import com.example.arturitopsicologo.Interface.InterfaceTaller;
import com.example.arturitopsicologo.Model.Atencion;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.Memoria;
import com.example.arturitopsicologo.Model.Paciente;
import com.example.arturitopsicologo.Presenter.PresenterPaciente;
import com.example.arturitopsicologo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CategoriaPacienteActivity extends AppCompatActivity implements LecturaFragment.OnFragmentInteractionListener,AtencionFragment.OnFragmentInteractionListener,MemoriaFragment.OnFragmentInteractionListener , InterfaceTaller {

    LecturaFragment fragment1;
    AtencionFragment fragment2;
    MemoriaFragment fragment3;
    String paciente_id;
    Button btn1,btn2,btn3;
    ImageView imgfinish;
    private DatabaseReference reference;
    PresenterPaciente presenterPaciente;

    TextView tvnombres,tvapellidos,tvedad;


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

        reference= FirebaseDatabase.getInstance().getReference();

        presenterPaciente= new PresenterPaciente(this,reference,"","","","");


    }

    private void inputs() {
        tvnombres=(TextView) findViewById(R.id.tvnombres);
        tvapellidos=(TextView) findViewById(R.id.tvapellidos);
        tvedad=(TextView) findViewById(R.id.tvedad);
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