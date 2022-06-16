package com.example.arturitopsicologo.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.arturitopsicologo.Fragment.EditFragment;
import com.example.arturitopsicologo.Fragment.PerfilFragment;
import com.example.arturitopsicologo.R;

public class PerfilActivity extends AppCompatActivity implements PerfilFragment.OnFragmentInteractionListener , View.OnClickListener {


    PerfilFragment fragment1;
    EditFragment fragment2;
    ImageView btnabrirgragment;
    String tipeFragment="perfil";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        fragment1=new PerfilFragment();
        fragment2=new EditFragment();

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContent, fragment1);
        ft.commit();

        inputs();

    }

    private void inputs() {
        btnabrirgragment=(ImageView) findViewById(R.id.btnabrirgragment);
        btnabrirgragment.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnabrirgragment:

                if (tipeFragment=="perfil"){
                    tipeFragment="edit";
                    FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentContent, fragment2);
                    ft.commit();
                }else{
                    tipeFragment ="perfil";
                    FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragmentContent, fragment1);
                    ft.commit();
                }
                break;
        }


    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}