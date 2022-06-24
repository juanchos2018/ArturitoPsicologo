package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.arturitopsicologo.Model.Atencion;
import com.example.arturitopsicologo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AtencionActivity extends AppCompatActivity  implements View.OnClickListener {

    ImageView imgciruclo,imgrectagulo,imgcuadrado,imgtriangulo,imgrombo;
    int cantidad=5;
    Button btnsiguiente;

    String CategoriaId="-N3iTztFqDubpdA0kT3h";
    private DatabaseReference reference;
    Atencion atencion;
    String figura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atencion);

        reference= FirebaseDatabase.getInstance().getReference();

        inputs();
    }

    private void inputs() {

        imgciruclo=(ImageView) findViewById(R.id.imgciruclo);
        imgrectagulo=(ImageView) findViewById(R.id.imgrectagulo);
        imgcuadrado=(ImageView) findViewById(R.id.imgcuadrado);
        imgtriangulo=(ImageView) findViewById(R.id.imgtriangulo);
        imgrombo=(ImageView) findViewById(R.id.imgrombo);
        btnsiguiente=(Button) findViewById(R.id.btnsiguiente);

        imgciruclo.setOnClickListener(this);
        imgrectagulo.setOnClickListener(this);
        imgcuadrado.setOnClickListener(this);
        imgtriangulo.setOnClickListener(this);
        imgrombo.setOnClickListener(this);
        btnsiguiente.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
          case   R.id.imgciruclo:
                figura="circulo";
                break;
            case   R.id.imgrectagulo:
                figura="rectangulo";
                break;
            case   R.id.imgcuadrado:
                figura="cuadrado";
                break;
            case   R.id.imgtriangulo:
                figura="triangulo";
                break;
            case   R.id.imgrombo:
                figura="rombo";
                break;
            case R.id.btnsiguiente:
                atencion = new Atencion();
                atencion.setCategoriaId(CategoriaId);
                atencion.setFigura(figura);
                atencion.setCantidad("5");
                atencion.setId(keyatencion());
                atencion.setCantidadclick("0");
                atencion.setCantidafiguras("0");
                atencion.setEstado("nuevo");

                Intent intent = new Intent(this,PacienteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("categoria_id",CategoriaId);
                bundle.putString("id",atencion.getId());
                bundle.putSerializable("object",atencion);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
        Toast.makeText(this, "selecciono "+figura, Toast.LENGTH_SHORT).show();
    }
    private   String keyatencion(){

        String NUMEROS = "0123456789";
        String MAYUSCULAS = "ABCDEFGHIJKLMNO_PQRSTUVWXYZ";
        String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
        String pswd = "";
        String key=NUMEROS+MAYUSCULAS+MINUSCULAS;
        for (int i = 0; i < 15; i++) {
            pswd+=(key.charAt((int)(Math.random() * key.length())));
        }
        return pswd;
    }

    private  void  ada(){

    }
}