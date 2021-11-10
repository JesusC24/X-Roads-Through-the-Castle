package com.jesusc24.xroadsthroughthecastle.data.model;

import java.util.Objects;

public class Chat {
    private String nombre, tipo, password, descripcion;
    private int id;

    public Chat(String nombre, String tipo, String descripcion, int id) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        Chat chat = (Chat) o;
        if(this.nombre.equals(chat.getNombre())) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, tipo, password, descripcion, id);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", password='" + password + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", id=" + id +
                '}';
    }
}
