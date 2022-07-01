package com.example.arturitopsicologo.Interface;

import android.content.Context;

import com.example.arturitopsicologo.Model.Atencion;
import com.example.arturitopsicologo.Model.Lectura;
import com.example.arturitopsicologo.Model.Memoria;
import com.example.arturitopsicologo.Model.Psicologo;

public interface InterfaceTaller {

    void onCallbackLectura(Lectura value);
    void onCallbackAtencion(Atencion value);
    void onCallbackMemoria(Memoria value);

    Context getContext();

}
