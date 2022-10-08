package com.example.arturitopsicologo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arturitopsicologo.Model.Atencion;
import com.example.arturitopsicologo.Model.Resultados;
import com.example.arturitopsicologo.R;

import java.util.ArrayList;

public class AdapterResultados extends RecyclerView.Adapter<AdapterResultados.ViewHolderDatos> implements View.OnClickListener {


    ArrayList<Resultados> listaItems;
    Context context;

    public AdapterResultados(ArrayList<Resultados> listaItems,Context context) {
        this.context=context;
        this.listaItems = listaItems;
    }


    private View.OnClickListener listener;

    public  void setOnClickListener(View.OnClickListener listener)
    {
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public AdapterResultados.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_resultados,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterResultados.ViewHolderDatos holder, int position) {

        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;
            datgolder.tvpunto.setText("Punto Lectura "+listaItems.get(position).getPuntoLectura());
            datgolder.tvpunto2.setText("Punto Atencion "+listaItems.get(position).getPuntoAtencion());
            datgolder.tvpunto3.setText("Punto Memoria "+listaItems.get(position).getPuntoMemoria());
            datgolder.tvfecha.setText(listaItems.get(position).getFecha());
        }
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        ImageView imgrectagulo;
        TextView tvpunto,tvpunto2,tvpunto3,tvfecha;
        ImageView imgestado;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            tvpunto=(TextView) itemView.findViewById(R.id.tvpunto);
            tvfecha=(TextView) itemView.findViewById(R.id.rvfecha);
            tvpunto2=(TextView) itemView.findViewById(R.id.tvpunto2);
            tvpunto3=(TextView) itemView.findViewById(R.id.tvpunto3);

        }
    }
}