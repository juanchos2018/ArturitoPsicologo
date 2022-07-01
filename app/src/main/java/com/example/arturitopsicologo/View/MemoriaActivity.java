package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.arturitopsicologo.Model.Memoria;
import com.example.arturitopsicologo.R;

public class MemoriaActivity extends AppCompatActivity  implements View.OnClickListener {




    ImageView imgluna,imgflobo,imgtele,imgpelota,imglapíz,imgperro,imgbici,imgcasa;
    ImageView imgcheck1,imgcheck2,imgcheck3,imgcheck4,imgcheck5,imgcheck6,imgcheck7,imgcheck8;
    Button btnsiguiente,btnlimpiar;


    int cantidad =3;
    int canticlick=0;
    String []figuras=new String[3];
    String CategoriaId="-N3iU-GjNXmOnKIUvK5F";
    ImageView imgfinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoria);


        inputs();

    }

    private void inputs() {
        imgluna=(ImageView) findViewById(R.id.imgluna);
        imgflobo=(ImageView) findViewById(R.id.imgflobo);
        imgtele=(ImageView) findViewById(R.id.imgtele);
        imgpelota=(ImageView) findViewById(R.id.imgpelota);
        imglapíz=(ImageView) findViewById(R.id.imglapíz);
        imgperro=(ImageView) findViewById(R.id.imgperro);
        imgbici=(ImageView) findViewById(R.id.imgbici);
        imgcasa=(ImageView) findViewById(R.id.imgcasa);
        imgcheck1=(ImageView) findViewById(R.id.imgcheck1);
        imgcheck2=(ImageView) findViewById(R.id.imgcheck2);
        imgcheck3=(ImageView) findViewById(R.id.imgcheck3);
        imgcheck4=(ImageView) findViewById(R.id.imgcheck4);
        imgcheck5=(ImageView) findViewById(R.id.imgcheck5);
        imgcheck6=(ImageView) findViewById(R.id.imgcheck6);
        imgcheck7=(ImageView) findViewById(R.id.imgcheck7);
        imgcheck8=(ImageView) findViewById(R.id.imgcheck8);


        btnlimpiar=(Button)findViewById(R.id.btnlimpiar);
        btnsiguiente=(Button)findViewById(R.id.btnsiguiente);

        imgfinish=(ImageView) findViewById(R.id.imgfinish);
        imgfinish.setOnClickListener(this);



        imgluna.setOnClickListener(this);
        imgflobo.setOnClickListener(this);
        imgtele.setOnClickListener(this);
        imgpelota.setOnClickListener(this);
        imglapíz.setOnClickListener(this);
        imgperro.setOnClickListener(this);
        imgbici.setOnClickListener(this);
        imgcasa.setOnClickListener(this);
        imgcheck1.setOnClickListener(this);
        imgcheck2.setOnClickListener(this);
        imgcheck3.setOnClickListener(this);
        imgcheck4.setOnClickListener(this);
        imgcheck5.setOnClickListener(this);
        imgcheck6.setOnClickListener(this);
        imgcheck7.setOnClickListener(this);
        imgcheck8.setOnClickListener(this);

        btnlimpiar.setOnClickListener(this);
        btnsiguiente.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case  R.id.imgluna:

                if (canticlick==3){
                    Toast.makeText(this, "ya tiene las 3 figuras", Toast.LENGTH_SHORT).show();
                }else{
                    figuras[canticlick]="luna";
                    canticlick++;
                    imgcheck1.setVisibility(View.VISIBLE);

                }
            break;

            case  R.id.imgflobo:
                if (canticlick==3){
                    Toast.makeText(this, "ya tiene las 3 figuras", Toast.LENGTH_SHORT).show();
                }else{
                    figuras[canticlick]="globo";
                    canticlick++;
                    imgcheck2.setVisibility(View.VISIBLE);

                }
                break;
            case  R.id.imgtele:
                if (canticlick==3){
                    Toast.makeText(this, "ya tiene las 3 figuras", Toast.LENGTH_SHORT).show();
                }else{
                    figuras[canticlick]="tele";
                    canticlick++;
                    imgcheck3.setVisibility(View.VISIBLE);

                }
                break;
            case  R.id.imgpelota:
                if (canticlick==3){
                    Toast.makeText(this, "ya tiene las 3 figuras", Toast.LENGTH_SHORT).show();
                }else{
                    figuras[canticlick]="pelota";
                    canticlick++;
                    imgcheck4.setVisibility(View.VISIBLE);

                }
                break;
            case  R.id.imglapíz:
                if (canticlick==3){
                    Toast.makeText(this, "ya tiene las 3 figuras", Toast.LENGTH_SHORT).show();
                }else{
                    figuras[canticlick]="lapiz";
                    canticlick++;
                    imgcheck5.setVisibility(View.VISIBLE);

                }
                break;

            case  R.id.imgperro:
                if (canticlick==3){
                    Toast.makeText(this, "ya tiene las 3 figuras", Toast.LENGTH_SHORT).show();
                }else{
                    figuras[canticlick]="perro";
                    canticlick++;
                    imgcheck6.setVisibility(View.VISIBLE);

                }
                break;
            case  R.id.imgbici:
                if (canticlick==3){
                    Toast.makeText(this, "ya tiene las 3 figuras", Toast.LENGTH_SHORT).show();
                }else{
                    figuras[canticlick]="bici";
                    canticlick++;
                    imgcheck7.setVisibility(View.VISIBLE);

                }
                break;
            case  R.id.imgcasa:
                if (canticlick==3){
                    Toast.makeText(this, "ya tiene las 3 figuras", Toast.LENGTH_SHORT).show();
                }else{
                    figuras[canticlick]="casa";
                    canticlick++;
                    imgcheck8.setVisibility(View.VISIBLE);
                }
                break;
            case  R.id.btnlimpiar:
                Limpiar();
                break;
            case  R.id.btnsiguiente:
                store();
                break;
            case R.id.imgfinish:
                finishs();
                break;

        }

    }
    private  void  finishs(){
        finish();
    }
    private void store() {

        Memoria memoria = new Memoria();
        memoria.setId(keyatencion());
        memoria.setCantidad("3");
        memoria.setCategoriaId(CategoriaId);
        memoria.setTitulo("Elejir las Figuras");
        memoria.setTiempo("30");
        memoria.setTiempodemorado("0");
        memoria.setEstado("Nuevo");
        memoria.setAudio("nulo");
        memoria.setFigura1(figuras[0]);
        memoria.setFigura2(figuras[1]);
        memoria.setFigura3(figuras[2]);

        Intent intent = new Intent(this,PacienteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("categoria_id",CategoriaId);
        bundle.putString("id",memoria.getId());
        bundle.putSerializable("object",memoria);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private  void  Limpiar(){
        canticlick=0;
        imgcheck1.setVisibility(View.INVISIBLE);
        imgcheck2.setVisibility(View.INVISIBLE);
        imgcheck3.setVisibility(View.INVISIBLE);
        imgcheck4.setVisibility(View.INVISIBLE);
        imgcheck5.setVisibility(View.INVISIBLE);
        imgcheck6.setVisibility(View.INVISIBLE);
        imgcheck7.setVisibility(View.INVISIBLE);
        imgcheck8.setVisibility(View.INVISIBLE);

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
}