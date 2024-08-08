package com.ingeniarinoxidables.sghiiwebservice.DTOs;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;

import java.util.List;

public class HerramientaResumenDto {

    private Long herramientasReg;
    private Long totalPiezas;
    private Long piezasActivas;
    private Long herramientasActivas;
    private Long herramientasDeBaja;
    private Long piezasDisponibles;
    private Long piezasKits;
    private Long piezasPrestamo;
    private List<Herramienta> ingresosL30d;
    private List<Herramienta> herramientaEscasa;

    public HerramientaResumenDto() {
    }

    public Long getHerramientasReg() {
        return herramientasReg;
    }

    public void setHerramientasReg(Long herramientasReg) {
        this.herramientasReg = herramientasReg;
    }

    public Long getHerramientasActivas() {
        return herramientasActivas;
    }

    public void setHerramientasActivas(Long herramientasActivas) {
        this.herramientasActivas = herramientasActivas;
    }

    public Long getHerramientasDeBaja() {
        return herramientasDeBaja;
    }

    public void setHerramientasDeBaja(Long herramientasDeBaja) {
        this.herramientasDeBaja = herramientasDeBaja;
    }

    public Long getTotalPiezas() {
        return totalPiezas;
    }

    public void setTotalPiezas(Long totalPiezas) {
        this.totalPiezas = totalPiezas;
    }

    public Long getPiezasActivas() {
        return piezasActivas;
    }

    public void setPiezasActivas(Long piezasActivas) {
        this.piezasActivas = piezasActivas;
    }

    public List<Herramienta> getIngresosL30d() {
        return ingresosL30d;
    }

    public void setIngresosL30d(List<Herramienta> ingresosL30d) {
        this.ingresosL30d = ingresosL30d;
    }

    public List<Herramienta> getHerramientaEscasa() {
        return herramientaEscasa;
    }

    public void setHerramientaEscasa(List<Herramienta> herramientaEscasa) {
        this.herramientaEscasa = herramientaEscasa;
    }

    public Long getPiezasDisponibles() {
        return piezasDisponibles;
    }

    public void setPiezasDisponibles(Long piezasDisponibles) {
        this.piezasDisponibles = piezasDisponibles;
    }

    public Long getPiezasKits() {
        return piezasKits;
    }

    public void setPiezasKits(Long piezasKits) {
        this.piezasKits = piezasKits;
    }

    public Long getPiezasPrestamo() {
        return piezasPrestamo;
    }

    public void setPiezasPrestamo(Long piezasPrestamo) {
        this.piezasPrestamo = piezasPrestamo;
    }
}
