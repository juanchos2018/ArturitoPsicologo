package com.example.arturitopsicologo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arturitopsicologo.Presenter.PresenterRegister;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener {


    EditText etcorreo,etclave;
    Button btningresar,btncrear;

    PresenterRegister presenterLogin;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputs();

        reference= FirebaseDatabase.getInstance().getReference();
        presenterLogin=new PresenterRegister(this,reference);

    }

    private void inputs() {
        etcorreo=(EditText)findViewById(R.id.etcorreo);
        etclave=(EditText)findViewById(R.id.etclave);
        btningresar=(Button) findViewById(R.id.btningresar);
        btncrear=(Button) findViewById(R.id.btncrear);

        btningresar.setOnClickListener(this);
        btncrear.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btncrear:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.btningresar:
                String correo=etcorreo.getText().toString();
                String clave =etclave.getText().toString();
                login(correo,clave);
                break;
        }
    }

    private void login(String correo, String clave) {
        if (TextUtils.isEmpty(correo)){
            etcorreo.setError("campo necesario");
        }
        else if (TextUtils.isEmpty(clave)){
            etclave.setError("campo necesario");
        }else {
            presenterLogin.login(correo,clave);
        }

    }
}