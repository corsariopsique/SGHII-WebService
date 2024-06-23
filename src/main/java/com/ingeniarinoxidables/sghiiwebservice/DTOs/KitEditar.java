package com.ingeniarinoxidables.sghiiwebservice.DTOs;

import java.util.List;

public class KitEditar {

    private String nombre;

    private String rol;

    private List<PaqueteHerramientasKit> herramientas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<PaqueteHerramientasKit> getHerramientas() {
        return herramientas;
    }

    public void setHerramientas(List<PaqueteHerramientasKit> herramientas) {
        this.herramientas = herramientas;
    }

    @Override
    public String toString() {
        return "KitEditar{" +
                "nombre='" + nombre + '\'' +
                ", rol='" + rol + '\'' +
                ", herramientas=" + herramientas +
                '}';
    }
}
