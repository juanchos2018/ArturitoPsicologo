package com.example.arturitopsicologo.Fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arturitopsicologo.Interface.InterfaceObjeto;
import com.example.arturitopsicologo.Interface.InterfaceTaller;
import com.example.arturitopsicologo.Model.Atencion;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.Memoria;
import com.example.arturitopsicologo.Model.Resultado;
import com.example.arturitopsicologo.Presenter.PresenterPsicologo;
import com.example.arturitopsicologo.Presenter.PresenterTaller;
import com.example.arturitopsicologo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LecturaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LecturaFragment extends Fragment implements InterfaceTaller {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private DatabaseReference reference;

    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private String user_id,paciente_id;
    PresenterTaller presenter;

    android.app.AlertDialog.Builder builder;
    AlertDialog alert;

    String CategoriaId= "-N3iTyAojHIFohK3ft4U";
    Resultado resultado2;
    String fecha_id;

    public LecturaFragment(String paciente_id) {
        // Required empty public constructor
        this.paciente_id=paciente_id;
    }

    int cantidadguardados=0;
    public LecturaFragment() {
        // Required empty public constructor

    }
    // TODO: Rename and change types and number of parameters
    public static LecturaFragment newInstance(String param1, String param2) {
        LecturaFragment fragment = new LecturaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_lectura, container, false);

        reference= FirebaseDatabase.getInstance().getReference();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        fecha_id = sdf.format(new Date());
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

        presenter=new PresenterTaller(getContext(),reference,paciente_id,this);

        records(view);

        return view;
    }


    private  void  records(View view){
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recylerlecturas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecyclerLectura(recyclerView,CategoriaId);
    }


    @Override
    public void onCallbackLectura(Lectura value) {
        viewResult(fecha_id,"");
        DialogoLectura(value);
    }

    @Override
    public void onCallbackAtencion(Atencion value) {

    }

    @Override
    public void onCallbackMemoria(Memoria value) {

    }

    private  void  DialogoLectura(Lectura obj){

        builder = new AlertDialog.Builder(getContext());
        Button btcerrrar;
        CheckBox che1,che2,che3;
        TextView etLectura,pregunta1,pregunta2,pregunta3,tvrespuesta1,tvrespuesta2,tvrespuesta3;
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_result_lectura, null);
        btcerrrar=(Button)v.findViewById(R.id.btnguardar);
        etLectura=(TextView)v.findViewById(R.id.etLectura);
        pregunta1=(TextView)v.findViewById(R.id.pregunta1);
        pregunta2=(TextView)v.findViewById(R.id.pregunta2);
        pregunta3=(TextView)v.findViewById(R.id.pregunta3);
        tvrespuesta1=(TextView)v.findViewById(R.id.tvrespuesta1);
        tvrespuesta2=(TextView)v.findViewById(R.id.tvrespuesta2);
        tvrespuesta3=(TextView)v.findViewById(R.id.tvrespuesta3);

        etLectura.setText(obj.getLectura());
        pregunta1.setText(obj.getPregunta1());
        pregunta2.setText(obj.getPregunta2());
        pregunta3.setText(obj.getPregunta3());

        tvrespuesta1.setText(obj.getRespuesta1());
        tvrespuesta2.setText(obj.getRespuesta2());
        tvrespuesta3.setText(obj.getRespuesta3());

        che1=(CheckBox)v.findViewById(R.id.che1);
        che2=(CheckBox)v.findViewById(R.id.che2);
        che3=(CheckBox)v.findViewById(R.id.che3);

        builder.setView(v);

        btcerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  puntosTotal=0;
                String chec1,chec2,chec3;
                if (che1.isChecked()){
                    chec1="true";
                    obj.setPuntores1("1");
                    puntosTotal=puntosTotal+1;
                }else {
                    chec1="false";
                    obj.setPuntores1("0");
                }
                if (che2.isChecked()){
                    chec2="true";
                    puntosTotal=puntosTotal+1;
                    obj.setPuntores2("1");
                }else {
                    chec2="false";
                    obj.setPuntores2("0");
                }
                if (che3.isChecked()){
                    chec3="true";
                    puntosTotal=puntosTotal+1;
                    obj.setPuntores3("1");
                }else {
                    chec3="false";
                    obj.setPuntores3("0");
                }

                Resultado resultado = new Resultado();

                resultado.setFecha_id(fecha_id);
                resultado.setPuntoLectura(String.valueOf(puntosTotal));
                resultado.setPuntoMemoria("");
                resultado.setPuntoAtencion("");
                resultado.setFecha(fecha_id);

                resultado.setPaciente_id(paciente_id);
                String tipoprueba="Lectura";

                if (resultado2==null){
                    presenter.saveResultLectura(resultado,fecha_id);
                }else{
                 //   savothres(resultado);
                    resultado2.setPuntoLectura(String.valueOf(puntosTotal));
                    presenter.updateResult(resultado2);
                }
                alert.dismiss();
            }
        });
        alert  = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
    }
    private  void updateResult(Resultado resultado){
        presenter.updateResult(resultado);
        //Toast.makeText(getContext(),"No es es nulo", Toast.LENGTH_SHORT).show();
    }

    public void  viewResult(String fecha_id, String tipoprueba){
        DatabaseReference reference2;
        reference2= FirebaseDatabase.getInstance().getReference("Resultado");
        reference2.child(paciente_id).child(fecha_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                resultado2 = snapshot.getValue(Resultado.class);
                if (snapshot.exists()){

                }else{

                }
            }
            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                Toast.makeText(getContext(), "Err "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}