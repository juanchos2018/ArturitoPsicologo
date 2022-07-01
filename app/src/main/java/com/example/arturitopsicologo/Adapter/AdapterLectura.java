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
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.R;
import com.example.arturitopsicologo.View.LecturaActivity;

import java.util.ArrayList;

public class AdapterLectura extends RecyclerView.Adapter<AdapterLectura.ViewHolderDatos> implements View.OnClickListener{

    ArrayList<Lectura> listaItems;
    Context context;
    private InterfaceClick interfaceClick;
    public AdapterLectura(ArrayList<Lectura> listaItems, Context context, InterfaceClick interfaceClick) {
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
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_lectura,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;
            datgolder.tvtitulo1.setText(listaItems.get(position).getTitulo());
            datgolder.id=listaItems.get(position).getId();
            datgolder.btneditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,  LecturaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",datgolder.id);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            datgolder.btnanular.setOnClickListener(new View.OnClickListener() {
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
        TextView tvtitulo1;
        Button btneditar,btnanular;
        String id;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvtitulo1=(TextView) itemView.findViewById(R.id.tvtitulo1);
            btneditar=(Button) itemView.findViewById(R.id.btneditar);
            btnanular=(Button) itemView.findViewById(R.id.btnanular);
        }
    }
}
