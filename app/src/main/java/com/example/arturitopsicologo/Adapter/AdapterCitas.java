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
import com.example.arturitopsicologo.Model.PsicoloPaciente;
import com.example.arturitopsicologo.R;
import com.example.arturitopsicologo.View.HorasActivity;

import java.util.ArrayList;

public class AdapterCitas extends RecyclerView.Adapter<AdapterCitas.ViewHolderDatos> implements View.OnClickListener {

    ArrayList<PsicoloPaciente> listaItems;
    Context context;
    String CategoriaId;

    public AdapterCitas(ArrayList<PsicoloPaciente> listaItems, Context context) {
        this.context=context;
        this.listaItems = listaItems;
        //this.interfaceClick=interfaceClick;
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
    public AdapterCitas.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_citas,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCitas.ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){

            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;
            datgolder.tvfecha.setText(listaItems.get(position).getFecha());
            datgolder.nombrepaciente.setText(listaItems.get(position).getNombres());
            datgolder.id=listaItems.get(position).getId();

        }
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView nombrepaciente,tvfecha;
        Button btnenviar;
        String id;
        String paciente_id;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nombrepaciente=(TextView) itemView.findViewById(R.id.nombrepaciente);
            tvfecha=(TextView) itemView.findViewById(R.id.tvfecha);
        }


    }
}
