package com.ingeniarinoxidables.sghiiwebservice.DTOs;

public class PaqueteHerramientasKit {
    private String id;
    private int cantidad;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "PaqueteHerramientasKit{" +
                "id='" + id + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}
