package com.example.arturitopsicologo.Fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arturitopsicologo.Interface.InterfaceTaller;
import com.example.arturitopsicologo.Model.Atencion;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.Memoria;
import com.example.arturitopsicologo.Model.Resultado;
import com.example.arturitopsicologo.Presenter.PresenterTaller;
import com.example.arturitopsicologo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AtencionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AtencionFragment extends Fragment implements InterfaceTaller {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    android.app.AlertDialog.Builder builder;
    AlertDialog alert;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private DatabaseReference reference;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private String user_id,paciente_id;
    PresenterTaller presenter;
    Resultado resultado2;

    String fecha_id;

    String CategoriaId="-N3iTztFqDubpdA0kT3h";

    public AtencionFragment(String paciente_id) {
        this.paciente_id=paciente_id;
    }
    public AtencionFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static AtencionFragment newInstance(String param1, String param2) {
        AtencionFragment fragment = new AtencionFragment();
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
        View view=inflater.inflate(R.layout.fragment_atencion, container, false);

        reference= FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        fecha_id = sdf.format(new Date());
        presenter=new PresenterTaller(getContext(),reference,paciente_id,this);
        records(view);

        return view;
    }

    private  void  records(View view){
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyleratenciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecyclerAtencion(recyclerView,CategoriaId);
    }

    @Override
    public void onCallbackLectura(Lectura value) {

    }

    @Override
    public void onCallbackAtencion(Atencion value) {

        viewResult(fecha_id,"");
        DialogoAtencion(value);
    }

    @Override
    public void onCallbackMemoria(Memoria value) {

    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    private  void  DialogoAtencion(Atencion obj){

        builder = new AlertDialog.Builder(getContext());

        Button btcerrrar;
        ImageView imageView;
        EditText etpuntaje;
        TextView tvcantidad,tvcantidadfigura,tvcantidadclick;
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_result_atencion, null);

        btcerrrar=(Button)v.findViewById(R.id.btnguardar);
        tvcantidad=(TextView)v.findViewById(R.id.tvcantidad);
        tvcantidadfigura=(TextView)v.findViewById(R.id.tvcantidadfigura);
        tvcantidadclick=(TextView)v.findViewById(R.id.tvcantidadclick);
        etpuntaje=(EditText)v.findViewById(R.id.etpuntaje);
        imageView=(ImageView)v.findViewById(R.id.ImageView);

        tvcantidad.setText(obj.getCantidad());
        tvcantidadfigura.setText(obj.getCantidafiguras());
        tvcantidadclick.setText(obj.getCantidadclick());

        if (obj.getFigura().equals("triangulo")){
            imageView.setImageResource(R.drawable.trianguloapp);
        }
        else if (obj.getFigura().equals("rectangulo")){
            imageView.setImageResource(R.drawable.rectaguloapp);
        }else if (obj.getFigura().equals("circulo")){
            imageView.setImageResource(R.drawable.circuloapp);
        }else if (obj.getFigura().equals("rombo")) {
            imageView.setImageResource(R.drawable.romboapp);
        }
        else if (obj.getFigura().equals("cuadrado")) {
            imageView.setImageResource(R.drawable.cuadradoapp);
        }

        builder.setView(v);
        btcerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String punto =etpuntaje.getText().toString();
                if (TextUtils.isEmpty(punto)){
                    Toast.makeText(getContext(), "agrege un puntaje", Toast.LENGTH_SHORT).show();
                }
                else{
                    obj.setPuntaje(punto);
                    obj.setCategoriaId(CategoriaId);

                    Resultado resultado = new Resultado();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                    String fecha_id = sdf.format(new Date());

                    resultado.setFecha_id(fecha_id);
                    resultado.setPuntoAtencion(punto);
                    resultado.setFecha(fecha_id);
                    resultado.setPuntoMemoria("");
                    resultado.setPuntoAtencion("");
                    resultado.setPaciente_id(paciente_id);

                    if (resultado2==null){
                        presenter.saveResultLectura(resultado,fecha_id);
                    }else{
                        resultado2.setPuntoAtencion(punto);
                        presenter.updateResult(resultado2);
                    }
                    alert.dismiss();
                   // updateAtencion(obj);
                    //saveResultLectura(resultado,fecha_id);

                }
            }
        });
        alert  = builder.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.show();
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
}