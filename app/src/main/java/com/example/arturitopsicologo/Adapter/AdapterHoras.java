package com.example.arturitopsicologo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arturitopsicologo.Interface.InterfaceClick;
import com.example.arturitopsicologo.Interface.InterfaceId;
import com.example.arturitopsicologo.Model.Fecha;
import com.example.arturitopsicologo.Model.Horario;
import com.example.arturitopsicologo.R;
import com.example.arturitopsicologo.View.HorasActivity;

import java.util.ArrayList;

public class AdapterHoras extends RecyclerView.Adapter<AdapterHoras.ViewHolderDatos> implements View.OnClickListener{


    ArrayList<Horario> listaItems;
    Context context;
    private InterfaceClick interfaceClick;
    public AdapterHoras(ArrayList<Horario> listaItems, Context context, InterfaceClick interfaceClick) {
        this.context=context;
        this.listaItems = listaItems;
        this.interfaceClick=interfaceClick;
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
    public AdapterHoras.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_horarios,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHoras.ViewHolderDatos holder, int position) {

        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;
            datgolder.tvhoras.setText(listaItems.get(position).getHora_inicio()+"-"+listaItems.get(position).getHora_fin());
            datgolder.id=listaItems.get(position).getId();
            datgolder.btneditarhoras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interfaceClick.onCallback(datgolder.id);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvhoras;
        Button btneditarhoras,btnhoras;
        String id;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvhoras=(TextView) itemView.findViewById(R.id.tvhoras);
            btneditarhoras=(Button) itemView.findViewById(R.id.btneditarhoras);
        }
    }
}
