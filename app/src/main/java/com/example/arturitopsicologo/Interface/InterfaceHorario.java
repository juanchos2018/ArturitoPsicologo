package com.example.arturitopsicologo.Interface;

import android.content.Context;

import com.example.arturitopsicologo.Model.Fecha;
import com.example.arturitopsicologo.Model.Horario;

public interface InterfaceHorario {

    void onCallback(Horario value);
    Context getContext();
}
