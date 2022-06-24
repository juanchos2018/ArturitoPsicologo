package com.example.arturitopsicologo.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.arturitopsicologo.Interface.InterfaceLectura;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Presenter.PresenterLectura;
import com.example.arturitopsicologo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LecturaActivity extends AppCompatActivity implements View.OnClickListener {


    LinearLayout layoutlectura,layoutcuestionario;
    RadioButton search,offer;
    EditText etpregunta1,etpregunta2,etpregunta3,etLectura,ettitulo;
    Button btnguardar,btnpublicar;

    PresenterLectura presenter;
    private DatabaseReference reference;
    String CategoriaId= "-N3iTyAojHIFohK3ft4U";
    String id;

    Lectura lecturaObjec;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura);

        layoutlectura=(LinearLayout)findViewById(R.id.layoutlectura);
        layoutcuestionario=(LinearLayout)findViewById(R.id.layoutcuestionario);

        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterLectura(this,reference);

        search=(RadioButton)findViewById(R.id.search);
        offer=(RadioButton)findViewById(R.id.offer);
        search.setChecked(true);
       // lectura = new Lectura();

        search.setOnClickListener(this);
        offer.setOnClickListener(this);
        offer.setTextColor(getColor(R.color.red));

        inputs();
        id=getIntent().getStringExtra("id");
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (id!=""){
            viewLectura(id);
            btnpublicar.setVisibility(View.VISIBLE);
        }
    }

    private void viewLectura(String id) {
        presenter.ViewLectura(value -> {
            ettitulo.setText(value.getTitulo());
            etLectura.setText(value.getLectura());
            etpregunta1.setText(value.getPregunta1());
            etpregunta2.setText(value.getPregunta2());
            etpregunta3.setText(value.getPregunta3());
            lecturaObjec=value;
            Toast.makeText(this, lecturaObjec.getPregunta1(), Toast.LENGTH_SHORT).show();

        }, CategoriaId,id);
    }

    private void inputs() {

        etpregunta1=(EditText) findViewById(R.id.pregunta1);
        etpregunta2=(EditText) findViewById(R.id.pregunta2);
        etpregunta3=(EditText) findViewById(R.id.pregunta3);
        etLectura=(EditText) findViewById(R.id.etLectura);
        ettitulo =(EditText) findViewById(R.id.ettitulo);

        btnguardar=(Button) findViewById(R.id.btnguardar);
        btnpublicar=(Button) findViewById(R.id.btnpublicar);
        btnguardar.setOnClickListener(this);
        btnpublicar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case  R.id.search:
                layoutlectura.setVisibility(View.VISIBLE);
                layoutcuestionario.setVisibility(View.GONE);
                break;

            case  R.id.offer:
                layoutlectura.setVisibility(View.GONE);
                layoutcuestionario.setVisibility(View.VISIBLE);
                break;

            case  R.id.btnguardar:

                String title =ettitulo.getText().toString();
                String lectura=etLectura.getText().toString();
                String preunga1=etpregunta1.getText().toString();
                String preunga2=etpregunta2.getText().toString();
                String preunga3=etpregunta3.getText().toString();
                store(title,lectura,preunga1,preunga2,preunga3);
                break;
            case R.id.btnpublicar:

               // String lectur_id="-N3iTyAojHIFohK3ft4U";
                Intent intent = new Intent(this,PacienteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("categoria_id",CategoriaId);
                bundle.putSerializable("id",lecturaObjec.getId());
                bundle.putSerializable("object",lecturaObjec);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }

    }

    private void store(String title, String lectura, String preunga1, String preunga2, String preunga3) {

        if (TextUtils.isEmpty(title)){
            ettitulo.setError("campo necesario");
        }else if (TextUtils.isEmpty(lectura)){
            etLectura.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(preunga1)){
            etpregunta1.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(preunga2)){
            etpregunta2.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(preunga3)){
            etpregunta3.setError("campo ncesario");
        }
        else{
            Lectura obj = new Lectura();
            obj.setId(id);
            obj.setCategoriaId(CategoriaId);
            obj.setTitulo(title);
            obj.setLectura(lectura);
            obj.setPregunta1(preunga1);
            obj.setPregunta2(preunga2);
            obj.setPregunta3(preunga3);
            obj.setRespuesta1("");
            obj.setRespuesta2("");
            obj.setRespuesta3("");
            obj.setEstado("nuevo");
            presenter.store(obj);
        }
    }
}