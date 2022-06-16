package com.example.arturitopsicologo.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.arturitopsicologo.Interface.InterfacePsicologo;
import com.example.arturitopsicologo.Model.Psicologo;
import com.example.arturitopsicologo.Presenter.PresenterPsicologo;
import com.example.arturitopsicologo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditFragment() {
        // Required empty public constructor
    }


    EditText etnombres,etapellidos,etphone,etformacion,etdescripcion;
    Button btnupdate,btnfoto;
    ImageView imgperfil;

    private DatabaseReference reference;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private String user_id;
    PresenterPsicologo presenter;
    private final int PICK_PHOTO=1;

    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
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
        View view=inflater.inflate(R.layout.fragment_edit, container, false);

        reference= FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        presenter=new PresenterPsicologo(getContext(),reference,storageReference);

        inputs(view);

        return view;
    }

    private void inputs(View view) {
        etnombres=(EditText) view.findViewById(R.id.etnombres);
        etapellidos=(EditText) view.findViewById(R.id.etapellidos);
        etphone=(EditText) view.findViewById(R.id.etphone);
        etformacion=(EditText) view.findViewById(R.id.etformacion);
        etdescripcion=(EditText) view.findViewById(R.id.etdescripcion);
        imgperfil=(ImageView)view.findViewById(R.id.idimgfotoperfil);

        btnupdate=(Button) view.findViewById(R.id.btnupdate);
        btnfoto=(Button) view.findViewById(R.id.btnfoto);


        btnupdate.setOnClickListener(this);
        btnfoto.setOnClickListener(this);

    }


    @Override
    public void onStart() {
        super.onStart();
        infoPsicologo();
    }
    private void infoPsicologo() {

        presenter.info(new InterfacePsicologo() {
            @Override
            public void onCallback(Psicologo value) {
                etnombres.setText(value.getNombres());
                etapellidos.setText(value.getApellido());
                etphone.setText(value.getPhone());
                etformacion.setText(value.getFormacion());

                if (value.getPhoto().equals("default")){
                    imgperfil.setImageResource(R.drawable.default_profile_image);
                }else{
                    Picasso.get().load(value.getPhoto()).fit().centerCrop().into(imgperfil);
                }
            }
        },user_id);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnupdate:
                String nombres =etnombres.getText().toString();
                String apellidos =etapellidos.getText().toString();
                String phone =etphone.getText().toString();
                String formacion =etformacion.getText().toString();
                String descripcion =etdescripcion.getText().toString();

                Update(nombres,apellidos,phone,formacion,descripcion);
                break;
            case  R.id.btnfoto:
                abrirGaleria();
                break;
        }
    }

    private void abrirGaleria(){
        Intent intent  = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"seleccione imagen"),PICK_PHOTO);
    }

    private void Update(String nombres, String apellidos, String phone, String formacion, String descripcion) {

        if (TextUtils.isEmpty(nombres)){
            etnombres.setError("campo ncesario");
        }
        else if (TextUtils.isEmpty(apellidos)){
            etapellidos.setError("campo necsario");
        }else if (TextUtils.isEmpty(phone)){
            etphone.setError("campo necesario");
        }
        else if (TextUtils.isEmpty(formacion)){
            etformacion.setError("campo necesario");
        }
        else if (TextUtils.isEmpty(descripcion)){
            etdescripcion.setError("campo necesario");
        }
        else {
            Psicologo psico= new Psicologo();
            psico.setId(user_id);
            psico.setNombres(nombres);
            psico.setApellido(apellidos);
            psico.setPhoto(phone);
            psico.setDescripcion(descripcion);
            psico.setFormacion(formacion);
            presenter.update(psico);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_PHOTO && resultCode==getActivity().RESULT_OK && data!=null && data.getData()!=null){

            Uri uri =data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                imgperfil.setImageBitmap(bitmap);
                presenter.saveImage(user_id,uri);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}