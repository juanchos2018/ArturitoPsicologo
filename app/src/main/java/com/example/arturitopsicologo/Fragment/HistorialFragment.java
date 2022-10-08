package com.example.arturitopsicologo.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.arturitopsicologo.Presenter.PresenterResultado;
import com.example.arturitopsicologo.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorialFragment extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BootonClickLisntener mListener;
    public String paciente_id;
    PresenterResultado presenter;
    private DatabaseReference reference;


    public HistorialFragment() {
        // Required empty public constructor
    }


    public static HistorialFragment newInstance() {
        HistorialFragment fragment = new HistorialFragment();
        return fragment;
    }

    // TODO: Rename and change types and number of parameters
//    public static HistorialFragment newInstance(String param1, String param2) {
//        HistorialFragment fragment = new HistorialFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        final View contentView = View.inflate(getContext(), R.layout.fragment_historial, null);


        reference = FirebaseDatabase.getInstance().getReference();
        presenter = new PresenterResultado(getContext(), reference,paciente_id);
        ListaHistorial(paciente_id, contentView);
       /// Toast.makeText(getContext(), paciente_id, Toast.LENGTH_SHORT).show();

        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
        // loadAddNotesFragments();
    }

    private void ListaHistorial(String paciente_id, View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclcerhistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        presenter.cargarHistorial(recyclerView);

    }

    public interface BootonClickLisntener {
        void onButtonclick(String texto);

        void copiartexto(String a);

        void EliminarArchivo(String keyarchivo);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            if (context instanceof BootonClickLisntener) {
                mListener = (BootonClickLisntener) context;
            } else {

                throw new ClassCastException(context.toString() + "musimpemet");
            }
            // mListener=(BootonClickLisntener)context;
        } catch (ClassCastException e) {

        }
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_historial, container, false);
//    }
}