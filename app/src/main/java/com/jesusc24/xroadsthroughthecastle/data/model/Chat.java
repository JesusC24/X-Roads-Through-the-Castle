package com.jesusc24.xroadsthroughthecastle.data.model;

import androidx.annotation.Nullable;

import java.io.Serializable;


/**
 * POJO Chat que se utilizar dentro de la entidad Foro
 */
public class Chat implements Comparable, Serializable {
    public static final String PUBLICO = "publico";
    public static final String PRIVADO = "privado";
    public static final String TAG = "chat";

    private String nombre, tipo, password, descripcion, confirmPassword;
    private int id;
    private boolean favorito;

    public Chat(String nombre, String tipo, String descripcion, int id) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.id = id;
        this.favorito = false;
    }

    public Chat() {

    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
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
    public String toString() {
        return "Chat{" +
                "nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", password='" + password + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return ((Chat)obj).getNombre().equals(getNombre());
    }


    @Override
    public int compareTo(Object o) {
        if(equals(o)) {
            return ((Chat)o).getDescripcion().compareTo(getDescripcion());
        } else {
            return ((Chat)o).getNombre().compareTo(getNombre());
        }
    }
}
