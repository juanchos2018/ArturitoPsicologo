package com.example.arturitopsicologo.Model;

import java.io.Serializable;

public class Lectura implements Serializable {

    String  id;
    String  CategoriaId;
    String  titulo;
    String  lectura;
    String  pregunta1;
    String  pregunta2;
    String  pregunta3;

    String  respuesta1;
    String  respuesta2;
    String  respuesta3;
    String  estado;

    String  status;
    String  puntores1;
    String  puntores2;
    String  puntores3;

    String  corregido;



    public  Lectura(){

    }


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

    public String getLectura() {
        return lectura;
    }

    public void setLectura(String lectura) {
        this.lectura = lectura;
    }

    public String getPregunta1() {
        return pregunta1;
    }

    public void setPregunta1(String pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public String getPregunta2() {
        return pregunta2;
    }

    public void setPregunta2(String pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public String getPregunta3() {
        return pregunta3;
    }

    public void setPregunta3(String pregunta3) {
        this.pregunta3 = pregunta3;
    }


    public String getRespuesta1() {
        return respuesta1;
    }

    public void setRespuesta1(String respuesta1) {
        this.respuesta1 = respuesta1;
    }

    public String getRespuesta2() {
        return respuesta2;
    }

    public void setRespuesta2(String respuesta2) {
        this.respuesta2 = respuesta2;
    }

    public String getRespuesta3() {
        return respuesta3;
    }

    public void setRespuesta3(String respuesta3) {
        this.respuesta3 = respuesta3;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getPuntores1() {
        return puntores1;
    }

    public void setPuntores1(String puntores1) {
        this.puntores1 = puntores1;
    }

    public String getPuntores2() {
        return puntores2;
    }

    public void setPuntores2(String puntores2) {
        this.puntores2 = puntores2;
    }

    public String getPuntores3() {
        return puntores3;
    }

    public void setPuntores3(String puntores3) {
        this.puntores3 = puntores3;
    }


    public String getCorregido() {
        return corregido;
    }

    public void setCorregido(String corregido) {
        this.corregido = corregido;
    }
}
