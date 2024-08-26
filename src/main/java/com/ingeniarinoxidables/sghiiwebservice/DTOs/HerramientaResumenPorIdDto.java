package com.ingeniarinoxidables.sghiiwebservice.DTOs;

import com.ingeniarinoxidables.sghiiwebservice.modelo.ItemHerramienta;

import java.util.List;

public class HerramientaResumenPorIdDto {

    private Long totalOper; // listo
    private Long operPrestamos; //
    private Long operDevoluciones; //
    private Long piezasPrestadas; //
    private Long piezasDevueltas; //
    private Long operL30d; //
    private Long totalOperarios; //
    private Long totalKits; //
    private List<ListadoOperariosTopDto> listaUsoOperarios; //
    private List<ListadoKitsTopDto> listaUsoKits; //

    private List<ItemHerramienta> itemsTool;

    public HerramientaResumenPorIdDto() {
    }

    public Long getTotalOper() {
        return totalOper;
    }

    public void setTotalOper(Long totalOper) {
        this.totalOper = totalOper;
    }

    public Long getOperPrestamos() {
        return operPrestamos;
    }

    public void setOperPrestamos(Long operPrestamos) {
        this.operPrestamos = operPrestamos;
    }

    public Long getOperDevoluciones() {
        return operDevoluciones;
    }

    public void setOperDevoluciones(Long operDevoluciones) {
        this.operDevoluciones = operDevoluciones;
    }

    public Long getPiezasPrestadas() {
        return piezasPrestadas;
    }

    public void setPiezasPrestadas(Long piezasPrestadas) {
        this.piezasPrestadas = piezasPrestadas;
    }

    public Long getPiezasDevueltas() {
        return piezasDevueltas;
    }

    public void setPiezasDevueltas(Long piezasDevueltas) {
        this.piezasDevueltas = piezasDevueltas;
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

    public Long getTotalKits() {
        return totalKits;
    }

    public void setTotalKits(Long totalKits) {
        this.totalKits = totalKits;
    }

    public List<ListadoOperariosTopDto> getListaUsoOperarios() {
        return listaUsoOperarios;
    }

    public void setListaUsoOperarios(List<ListadoOperariosTopDto> listaUsoOperarios) {
        this.listaUsoOperarios = listaUsoOperarios;
    }

    public List<ListadoKitsTopDto> getListaUsoKits() {
        return listaUsoKits;
    }

    public void setListaUsoKits(List<ListadoKitsTopDto> listaUsoKits) {
        this.listaUsoKits = listaUsoKits;
    }

    public List<ItemHerramienta> getItemsTool() {
        return itemsTool;
    }

    public void setItemsTool(List<ItemHerramienta> itemsTool) {
        this.itemsTool = itemsTool;
    }
}
