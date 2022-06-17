package com.example.arturitopsicologo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arturitopsicologo.Interface.InterfaceFecha;
import com.example.arturitopsicologo.Interface.InterfaceId;
import com.example.arturitopsicologo.Model.Fecha;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.R;
import com.example.arturitopsicologo.View.HorasActivity;
import com.example.arturitopsicologo.View.LecturaActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterFechas extends RecyclerView.Adapter<AdapterFechas.ViewHolderDatos> implements View.OnClickListener{

    ArrayList<Fecha> listaItems;
    Context context;

    private InterfaceId interfaceFecha;

    public AdapterFechas(ArrayList<Fecha> listaItems,Context context,InterfaceId interfaceFecha) {
        this.context=context;
        this.listaItems = listaItems;
        this.interfaceFecha = interfaceFecha;
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
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_fechas,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;
            datgolder.tvfecha.setText(listaItems.get(position).getFecha());
            datgolder.id=listaItems.get(position).getId();
            datgolder.btneditarfecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interfaceFecha.getId(datgolder.id);
                }
            });
            datgolder.btnhoras.setOnClickListener(view -> {
                Intent intent = new Intent(context, HorasActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fecha_id",datgolder.id);
                intent.putExtras(bundle);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView tvfecha;
        Button btneditarfecha,btnhoras;
        String id;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvfecha=(TextView) itemView.findViewById(R.id.tvfecha);
            btneditarfecha=(Button) itemView.findViewById(R.id.btneditarfecha);
            btnhoras=(Button) itemView.findViewById(R.id.btnhoras);
        }
    }
}
