package com.example.arturitopsicologo.Fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MemoriaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemoriaFragment extends Fragment  implements InterfaceTaller {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DatabaseReference reference;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private String user_id,paciente_id;
    PresenterTaller presenter;
    String CategoriaId="-N3iU-GjNXmOnKIUvK5F";
    Resultado resultado2;

    android.app.AlertDialog.Builder builder;
    AlertDialog alert;
    String fecha_id;
    

    private String mParam1;
    private String mParam2;

    public MemoriaFragment(String paciente_id) {
       this.paciente_id=paciente_id;
    }
    public MemoriaFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static MemoriaFragment newInstance(String param1, String param2) {
        MemoriaFragment fragment = new MemoriaFragment();
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
        View view=inflater.inflate(R.layout.fragment_memoria, container, false);

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
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recylerMemorias);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        presenter.cargarRecyclerMemoria(recyclerView,CategoriaId);
    }

    @Override
    public void onCallbackLectura(Lectura value) {

    }

    @Override
    public void onCallbackAtencion(Atencion value) {

    }

    @Override
    public void onCallbackMemoria(Memoria value) {
        viewResult(fecha_id,"");
        DialogoMemoria(value);
    }

    private void DialogoMemoria(Memoria value) {
        builder = new AlertDialog.Builder(getContext());
        Button btcerrrar;

        EditText etpuntaje;
        ImageView imgplay;
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialogo_result_memoria, null);
        Toast.makeText(getContext(), value.getAudio(), Toast.LENGTH_SHORT).show();
        btcerrrar=(Button)v.findViewById(R.id.btnguardar);
        etpuntaje=(EditText)v.findViewById(R.id.etpuntaje);
        imgplay=(ImageView)v.findViewById(R.id.imgplay);

        builder.setView(v);
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value.getAudio().equals("nulo")){
                    Toast.makeText(getContext(), "no hay audio", Toast.LENGTH_SHORT).show();
                }
                else{
                    String audioUrl = value.getAudio();
                    MediaPlayer mediaPlayer;
                    // initializing media player
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mediaPlayer.setDataSource(audioUrl);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btcerrrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String puntaje =etpuntaje.getText().toString();
                Resultado resultado = new Resultado();

                resultado.setFecha_id(fecha_id);
                resultado.setPuntoLectura("");
                resultado.setPuntoMemoria(puntaje);
                resultado.setPuntoAtencion("");
                resultado.setFecha(fecha_id);

                resultado.setPaciente_id(paciente_id);
                String tipoprueba="Memoria";

                if (resultado2==null){
                    presenter.saveResultLectura(resultado,fecha_id);
                }else{
                    resultado2.setPuntoMemoria(puntaje);
                    presenter.updateResult(resultado2);
                }
                alert.dismiss();

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
    
    
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}