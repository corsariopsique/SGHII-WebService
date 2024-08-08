package com.ingeniarinoxidables.sghiiwebservice.DTOs;

import java.util.List;

public class KitResumenPorIdDto {

    private Long operPrestamo;
    private Long operDevolucion;
    private Long operL30d;
    private Long totalOperarios;
    private List<ListadoOperariosTopDto> listaUsoOperarios;

    public KitResumenPorIdDto() {
    }

    public Long getOperPrestamo() {
        return operPrestamo;
    }

    public void setOperPrestamo(Long operPrestamo) {
        this.operPrestamo = operPrestamo;
    }

    public Long getOperDevolucion() {
        return operDevolucion;
    }

    public void setOperDevolucion(Long operDevolucion) {
        this.operDevolucion = operDevolucion;
    }

    public Long getOperL30d() {
        return operL30d;
    }

    public void setOperL30d(Long operL30d) {
        this.operL30d = operL30d;
    }

    public Long getTotalOperarios() {
        return totalOperarios;
    }

    public void setTotalOperarios(Long totalOperarios) {
        this.totalOperarios = totalOperarios;
    }

    public List<ListadoOperariosTopDto> getListaUsoOperarios() {
        return listaUsoOperarios;
    }

    public void setListaUsoOperarios(List<ListadoOperariosTopDto> listaUsoOperarios) {
        this.listaUsoOperarios = listaUsoOperarios;
    }
}
