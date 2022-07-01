package com.example.arturitopsicologo.Fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arturitopsicologo.Interface.InterfacePsicologo;
import com.example.arturitopsicologo.Model.Psicologo;
import com.example.arturitopsicologo.Presenter.PresenterPsicologo;
import com.example.arturitopsicologo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class PerfilFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilFragment() {
        // Required empty public constructor
    }
    private DatabaseReference reference;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private String user_id;
    PresenterPsicologo presenter;

    TextView tvnonbre,tvapellidos,tvtelefono,tvcorreo;
    ImageView imgperfil;


    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
        View view =inflater.inflate(R.layout.fragment_perfil, container, false);

        reference= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        presenter=new PresenterPsicologo(getContext(),reference,storageReference);
        inputs(view);
        return view;
    }

    private void inputs(View view) {

        tvnonbre=(TextView) view.findViewById(R.id.tvnombre);
        tvapellidos=(TextView) view.findViewById(R.id.tvapellidos);
        tvtelefono=(TextView) view.findViewById(R.id.tvtelefono);
        tvcorreo=(TextView) view.findViewById(R.id.tvcorreo);
        imgperfil=(ImageView) view.findViewById(R.id.img4);
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
                tvnonbre.setText(value.getNombres());
                tvapellidos.setText(value.getApellido());
                tvcorreo.setText(value.getCorreo());
                tvtelefono.setText(value.getPhone());
                if (value.getPhoto().equals("default")){
                    imgperfil.setImageResource(R.drawable.default_profile_image);
                }else{
                    Picasso.get().load(value.getPhoto()).fit().centerCrop().into(imgperfil);
                }
            }
        },user_id);
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }


}