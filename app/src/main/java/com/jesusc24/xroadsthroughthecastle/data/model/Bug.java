package com.jesusc24.xroadsthroughthecastle.data.model;

import java.io.Serializable;

/**
 * POJO Bug que se utiliza en la entidad bug
 */
public class Bug implements Comparable, Serializable {
    public static final String TAG = "Bug";
    String nombre, descripcion, tipo, gravedad, dispositivo, so, estado;
    int id;

    public Bug(String nombre, String tipo, String gravedad, String dispositivo, String so, String estado, String descripcion, int id) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.gravedad = gravedad;
        this.dispositivo = dispositivo;
        this.so = so;
        this.descripcion = descripcion;
        this.estado = estado;
        this.id = id;
    }

    public Bug() {

    }

    public Bug(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGravedad() {
        return gravedad;
    }

    public void setGravedad(String gravedad) {
        this.gravedad = gravedad;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Bug)obj).getNombre().equals(getNombre());
    }

    @Override
    public String toString() {
        return "Bug{" +
                "nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", gravedad='" + gravedad + '\'' +
                ", dispositivo='" + dispositivo + '\'' +
                ", so='" + so + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public int compareTo(Object obj) {
        if(equals(obj)) {
            return ((Bug)obj).getDescripcion().compareTo(getDescripcion());
        } else {
            return ((Bug)obj).getNombre().compareTo(getNombre());
        }
    }
}
