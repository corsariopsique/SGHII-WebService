package com.ingeniarinoxidables.sghiiwebservice.DTOs;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;

public class ListadoHerramientasTopDto {

    private Herramienta tool;
    private Long cantidad;

    public ListadoHerramientasTopDto() {
    }

    public Herramienta getTool() {
        return tool;
    }

    public void setTool(Herramienta tool) {
        this.tool = tool;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
