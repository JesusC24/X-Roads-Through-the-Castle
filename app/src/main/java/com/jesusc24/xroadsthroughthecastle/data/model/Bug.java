package com.jesusc24.xroadsthroughthecastle.data.model;

import java.util.Objects;

public class Bug {
    String nombre, tipo, gravedad, dispositivo, so, descripcion;
    int id;

    public Bug(String nombre, String tipo, String gravedad, String dispositivo, String so, String descripcion, int id) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.gravedad = gravedad;
        this.dispositivo = dispositivo;
        this.so = so;
        this.descripcion = descripcion;
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bug bug = (Bug) o;
        return id == bug.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
}
