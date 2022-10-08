package com.example.arturitopsicologo.Model;

public class Historial {

    private String paciente_id;
    private String fecha;
    private String hora;
    private String titulo;
    private String descripcion;
    private String categoria;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPaciente_id() {
        return paciente_id;
    }

    public void setPaciente_id(String paciente_id) {
        this.paciente_id = paciente_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
