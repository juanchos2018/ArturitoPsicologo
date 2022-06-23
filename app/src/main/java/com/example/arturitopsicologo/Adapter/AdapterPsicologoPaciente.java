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
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.PsicoloPaciente;
import com.example.arturitopsicologo.R;
import com.example.arturitopsicologo.View.LecturaActivity;

import java.util.ArrayList;

public class AdapterPsicologoPaciente  extends RecyclerView.Adapter<AdapterPsicologoPaciente.ViewHolderDatos> implements View.OnClickListener{

    ArrayList<PsicoloPaciente> listaItems;
    Context context;
    String CategoriaId;


    private InterfaceClick interfaceClick;

    public AdapterPsicologoPaciente(ArrayList<PsicoloPaciente> listaItems,Context context,InterfaceClick interfaceClick) {
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
    public AdapterPsicologoPaciente.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_psicologo_paciente,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPsicologoPaciente.ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;
            datgolder.tvnombrePa.setText(listaItems.get(position).getNombres());
            datgolder.id=listaItems.get(position).getId();
            datgolder.paciente_id=listaItems.get(position).getPaciente_id();
            datgolder.btnenviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interfaceClick.onCallback(datgolder.paciente_id);
                }
            });
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
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvnombrePa=(TextView) itemView.findViewById(R.id.tvnombrePa);
            btnenviar=(Button) itemView.findViewById(R.id.btnenviar);
        }
    }
}
