package com.example.arturitopsicologo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arturitopsicologo.Model.PsicoloPaciente;
import com.example.arturitopsicologo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterPacientes2  extends RecyclerView.Adapter<AdapterPacientes2.ViewHolderDatos> implements View.OnClickListener {

    ArrayList<PsicoloPaciente> listaItems;
    Context context;
    String CategoriaId;


    public AdapterPacientes2(ArrayList<PsicoloPaciente> listaItems, Context context) {
        this.listaItems = listaItems;
        this.context = context;
    }

    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pacientes2, parent, false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos) {
            final ViewHolderDatos datgolder = (ViewHolderDatos) holder;
            datgolder.tvnombrePa.setText(listaItems.get(position).getNombres());

            if (listaItems.get(position).getPhoto().equals("default")) {
                datgolder.imgfoto.setImageResource(R.drawable.default_profile_image);
            } else {
                Picasso.get().load(listaItems.get(position).getPhoto()).fit().centerCrop().into(datgolder.imgfoto);
            }
        }
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView tvnombrePa;
        Button btnenviar;
        String id;
        String paciente_id;
        ImageView imgfoto;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvnombrePa = (TextView) itemView.findViewById(R.id.tvnombre);
            imgfoto = (ImageView) itemView.findViewById(R.id.imgfoto);

        }
    }
}