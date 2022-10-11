package com.example.arturitopsicologo.Model;


import java.io.Serializable;

public class Herramienta implements Serializable {


    private  String  id;
    private  String  CategoriaId;
    private  String  titulo;
    private  String  url_imagen;
    private  String  url_imagen_resolv;


    private  String puntaje;
    private  String estado;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoriaId() {
        return CategoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        CategoriaId = categoriaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getUrl_imagen_resolv() {
        return url_imagen_resolv;
    }

    public void setUrl_imagen_resolv(String url_imagen_resolv) {
        this.url_imagen_resolv = url_imagen_resolv;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
