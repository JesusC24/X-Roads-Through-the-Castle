package com.jesusc24.xroadsthroughthecastle.data.model;

public class Mensaje {
    String texto, usuario, fecha_envio;

    public Mensaje(String texto, String usuario, String fecha_envio) {
        this.texto = texto;
        this.usuario = usuario;
        this.fecha_envio = fecha_envio;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(String fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "texto='" + texto + '\'' +
                ", usuario='" + usuario + '\'' +
                ", fecha_envio='" + fecha_envio + '\'' +
                '}';
    }
}

