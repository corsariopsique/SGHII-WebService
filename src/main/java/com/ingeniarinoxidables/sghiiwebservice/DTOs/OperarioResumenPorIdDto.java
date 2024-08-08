package com.ingeniarinoxidables.sghiiwebservice.DTOs;

import java.util.List;

public class OperarioResumenPorIdDto {
    private int totalOper;
    private Long prestamos;
    private Long devoluciones;
    private Long operL30d;
    private List<ListadoHerramientasTopDto> listaUsoTools;
    private List<ListadoKitsTopDto> listaUsoKits;
    private int totalTools;
    private int totalKits;

    public OperarioResumenPorIdDto() {
    }

    public int getTotalTools() {
        return totalTools;
    }

    public void setTotalTools(int totalTools) {
        this.totalTools = totalTools;
    }

    public int getTotalKits() {
        return totalKits;
    }

    public void setTotalKits(int totalKits) {
        this.totalKits = totalKits;
    }

    public int getTotalOper() {
        return totalOper;
    }

    public void setTotalOper(int totalOper) {
        this.totalOper = totalOper;
    }

    public Long getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(Long prestamos) {
        this.prestamos = prestamos;
    }

    public Long getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(Long devoluciones) {
        this.devoluciones = devoluciones;
    }

    public Long getOperL30d() {
        return operL30d;
    }

    public void setOperL30d(Long operL30d) {
        this.operL30d = operL30d;
    }

    public List<ListadoHerramientasTopDto> getListaUsoTools() {
        return listaUsoTools;
    }

    public void setListaUsoTools(List<ListadoHerramientasTopDto> listaUsoTools) {
        this.listaUsoTools = listaUsoTools;
    }

    public List<ListadoKitsTopDto> getListaUsoKits() {
        return listaUsoKits;
    }

    public void setListaUsoKits(List<ListadoKitsTopDto> listaUsoKits) {
        this.listaUsoKits = listaUsoKits;
    }
}
