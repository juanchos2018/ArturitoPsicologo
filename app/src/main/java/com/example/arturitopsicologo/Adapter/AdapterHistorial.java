package com.example.arturitopsicologo.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arturitopsicologo.Interface.InterfaceClick;
import com.example.arturitopsicologo.Model.Historial;
import com.example.arturitopsicologo.Model.Horario;
import com.example.arturitopsicologo.R;

import java.util.ArrayList;

public class AdapterHistorial extends RecyclerView.Adapter<AdapterHistorial.ViewHolderDatos> implements View.OnClickListener{


    ArrayList<Historial> listaItems;
    Context context;
    private InterfaceClick interfaceClick;
    public AdapterHistorial(ArrayList<Historial> listaItems, Context context) {
        this.context=context;
        this.listaItems = listaItems;
       // this.interfaceClick=interfaceClick;
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
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.item_historial,parent,false);
        vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {

        if (holder instanceof ViewHolderDatos){
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;
            datgolder.tvtitulo.setText(listaItems.get(position).getTitulo());
            datgolder.tvdescripcion.setText(listaItems.get(position).getDescripcion());
//            datgolder.id=listaItems.get(position).getId();
        }
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView tvtitulo,tvdescripcion;
        Button btneditarhoras,btnhoras;
        String id;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvtitulo=(TextView) itemView.findViewById(R.id.tvtitulo);
            tvdescripcion=(TextView) itemView.findViewById(R.id.tvdescripcion);

        }
    }
}
