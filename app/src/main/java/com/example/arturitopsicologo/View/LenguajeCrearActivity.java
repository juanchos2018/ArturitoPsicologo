package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.Lenguaje;
import com.example.arturitopsicologo.Presenter.PresenterLectura;
import com.example.arturitopsicologo.Presenter.PresenterLenguaje;
import com.example.arturitopsicologo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LenguajeCrearActivity extends AppCompatActivity implements View.OnClickListener {


    EditText txtpalabra1,txtpalabra2,txtpalabra3,txtpalabra4,txtpalabra5,txtpalabra6,txtpalabra7,txtpalabra8,txtpalabra9,txtpalabra10,txtpalabra11,txtpalabra12,txtpalabra13,txtpalabra14,txtpalabra15;

    Button btnguardar;
    PresenterLenguaje presenter;
    private DatabaseReference reference;
    String CategoriaId= "-N1iLENGUAJE0000t4U";
    String id;

    private FirebaseAuth mAuth;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lenguaje_crear);


        inputs();

        btnguardar.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterLenguaje(this,reference,user_id,CategoriaId);

    }

    private void inputs() {

        txtpalabra1=(EditText) findViewById(R.id.txtpalabra1);
        txtpalabra2=(EditText) findViewById(R.id.txtpalabra2);
        txtpalabra3=(EditText) findViewById(R.id.txtpalabra3);
        txtpalabra4=(EditText) findViewById(R.id.txtpalabra4);
        txtpalabra5=(EditText) findViewById(R.id.txtpalabra5);
        txtpalabra6=(EditText) findViewById(R.id.txtpalabra6);
        txtpalabra7=(EditText) findViewById(R.id.txtpalabra7);
        txtpalabra8=(EditText) findViewById(R.id.txtpalabra8);
        txtpalabra9=(EditText) findViewById(R.id.txtpalabra9);
        txtpalabra10=(EditText) findViewById(R.id.txtpalabra10);
        txtpalabra11=(EditText) findViewById(R.id.txtpalabra11);
        txtpalabra12=(EditText) findViewById(R.id.txtpalabra12);
        txtpalabra13=(EditText) findViewById(R.id.txtpalabra13);
        txtpalabra14=(EditText) findViewById(R.id.txtpalabra14);
        txtpalabra15=(EditText) findViewById(R.id.txtpalabra15);

        btnguardar=(Button) findViewById(R.id.btnguardar);

        txtpalabra1.setText("Rosa");
        txtpalabra2.setText("Espada");
        txtpalabra3.setText("Escalera");
        txtpalabra4.setText("Almeja");
        txtpalabra5.setText("Pardo");
        txtpalabra6.setText("Ermita");
        txtpalabra7.setText("Prudente");
        txtpalabra8.setText("Cromo");
        txtpalabra9.setText("Gracioso");
        txtpalabra10.setText("Transparente");
        txtpalabra11.setText("Dragon");
        txtpalabra12.setText("Esterelidad");
        txtpalabra13.setText("Influencia");
        txtpalabra14.setText("Paradera");
        txtpalabra15.setText("Entrada");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnguardar:

                String p1 =txtpalabra1.getText().toString();
                String p2 =txtpalabra2.getText().toString();
                String p3 =txtpalabra3.getText().toString();
                String p4 =txtpalabra4.getText().toString();
                String p5 =txtpalabra5.getText().toString();
                String p6 =txtpalabra6.getText().toString();
                String p7 =txtpalabra7.getText().toString();
                String p8 =txtpalabra8.getText().toString();
                String p9 =txtpalabra9.getText().toString();
                String p10 =txtpalabra10.getText().toString();
                String p11 =txtpalabra11.getText().toString();
                String p12 =txtpalabra12.getText().toString();
                String p13 =txtpalabra13.getText().toString();
                String p14 =txtpalabra14.getText().toString();
                String p15 =txtpalabra15.getText().toString();

                store(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15);

                break;
        }

    }


    private void store(String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8, String p9, String p10, String p11, String p12, String p13, String p14, String p15) {

        if (TextUtils.isEmpty(p1)){
            txtpalabra1.setError("campo necesario");
        }else if (TextUtils.isEmpty(p2)){
            txtpalabra2.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(p3)){
            txtpalabra3.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(p4)){
            txtpalabra4.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(p5)){
            txtpalabra5.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(p6)){
            txtpalabra6.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(p7)){
            txtpalabra7.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(p8)){
            txtpalabra8.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(p9)){
            txtpalabra9.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(p10)){
            txtpalabra10.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(p11)){
            txtpalabra11.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(p12)){
            txtpalabra12.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(p13)){
            txtpalabra13.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(p14)){
            txtpalabra14.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(p15)){
            txtpalabra15.setError("campo ncesario");
        }
        else{

            Lenguaje obj = new Lenguaje();
            obj.setId(id);
            obj.setCategoriaId(CategoriaId);
            obj.setTitulo("Lenguaje Arituculado");
            obj.setAudio("");
            obj.setEstado("nuevo");
            obj.setId("");
            obj.setPalabra1(p1);
            obj.setPalabra2(p2);
            obj.setPalabra3(p3);
            obj.setPalabra4(p4);
            obj.setPalabra5(p5);
            obj.setPalabra6(p6);
            obj.setPalabra7(p7);
            obj.setPalabra8(p8);
            obj.setPalabra9(p9);
            obj.setPalabra10(p10);
            obj.setPalabra11(p11);
            obj.setPalabra12(p12);
            obj.setPalabra13(p13);
            obj.setPalabra14(p14);
            obj.setPalabra15(p15);

            obj.setPunto_pa1("0");
            obj.setPunto_pa2("0");
            obj.setPunto_pa3("0");
            obj.setPunto_pa4("0");
            obj.setPunto_pa5("0");
            obj.setPunto_pa6("0");
            obj.setPunto_pa7("0");
            obj.setPunto_pa8("0");
            obj.setPunto_pa9("0");
            obj.setPunto_pa10("0");
            obj.setPunto_pa11("0");
            obj.setPunto_pa12("0");
            obj.setPunto_pa13("0");
            obj.setPunto_pa14("0");
            obj.setPunto_pa15("0");

            presenter.store(obj);

        }
    }
}