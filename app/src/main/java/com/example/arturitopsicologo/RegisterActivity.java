package com.example.arturitopsicologo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arturitopsicologo.Presenter.PresenterRegister;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {



    EditText tvnombre,tvapellido,tvcorreo,tvclave;
    Button btnregistrarusu;
    PresenterRegister presenterLogin;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reference= FirebaseDatabase.getInstance().getReference();
        presenterLogin=new PresenterRegister(this,reference);

        inputs();

    }

    private void inputs() {

        tvnombre=(EditText)findViewById(R.id.tvnombre);
        tvapellido=(EditText)findViewById(R.id.tvapellido);
        tvcorreo=(EditText)findViewById(R.id.tvcorreo);
        tvclave=(EditText)findViewById(R.id.tvclave);
        btnregistrarusu=(Button) findViewById(R.id.btnregistrarusu);
        btnregistrarusu.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnregistrarusu:
                String nombre=tvnombre.getText().toString();
                String apellido=tvapellido.getText().toString();
                String correo=tvcorreo.getText().toString();
                String clave=tvclave.getText().toString();

                store(nombre,apellido,correo,clave);
                break;
        }
    }

    private  void store(String nombre, String apellido, String correo, String clave){
        if (TextUtils.isEmpty(nombre)) {
            tvnombre.setError("campo necesario");
        }else if (TextUtils.isEmpty(apellido)){
            tvapellido.setError("campo necesario");
        }
        else if (TextUtils.isEmpty(correo)){
            tvcorreo.setError("campo necesario");
        }
        else if (TextUtils.isEmpty(clave)){
            tvclave.setError("campo necesario");
        }else {
            presenterLogin.register(nombre,apellido,correo,clave);
        }
    }

}