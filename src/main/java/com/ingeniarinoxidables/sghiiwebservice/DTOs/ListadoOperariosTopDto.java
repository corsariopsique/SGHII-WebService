package com.ingeniarinoxidables.sghiiwebservice.DTOs;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;

public class ListadoOperariosTopDto {

    private Operario operario;
    private Long cantidad;

    public ListadoOperariosTopDto() {
    }

    public Operario getOperario() {
        return operario;
    }

    public void setOperario(Operario operario) {
        this.operario = operario;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
