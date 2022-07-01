package com.example.arturitopsicologo.Model;

public class Resultado {

    private String fecha_id;
    private String paciente_id;
    private String puntoLectura;
    private String puntoAtencion;
    private String puntoMemoria;
    private String fecha;
    private String puntoTotal;

    public String getFecha_id() {
        return fecha_id;
    }

    public void setFecha_id(String fecha_id) {
        this.fecha_id = fecha_id;
    }

    public String getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(String paciente_id) {
        this.paciente_id = paciente_id;
    }

    public String getPuntoLectura() {
        return puntoLectura;
    }

    public void setPuntoLectura(String puntoLectura) {
        this.puntoLectura = puntoLectura;
    }

    public String getPuntoAtencion() {
        return puntoAtencion;
    }

    public void setPuntoAtencion(String puntoAtencion) {
        this.puntoAtencion = puntoAtencion;
    }

    public String getPuntoMemoria() {
        return puntoMemoria;
    }

    public void setPuntoMemoria(String puntoMemoria) {
        this.puntoMemoria = puntoMemoria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPuntoTotal() {
        return puntoTotal;
    }

    public void setPuntoTotal(String puntoTotal) {
        this.puntoTotal = puntoTotal;
    }
}
