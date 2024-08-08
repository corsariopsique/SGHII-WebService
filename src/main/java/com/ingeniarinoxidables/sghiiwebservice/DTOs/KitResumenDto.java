package com.ingeniarinoxidables.sghiiwebservice.DTOs;

public class KitResumenDto {
    private int kitsReg;
    private int totalPiezasKits;
    private int kitsActivos;
    private int kitsDeBaja;
    private int kitsPrestados;
    private int kitsDisponibles;

    public KitResumenDto() {
    }

    public int getKitsReg() {
        return kitsReg;
    }

    public void setKitsReg(int kitsReg) {
        this.kitsReg = kitsReg;
    }

    public int getTotalPiezasKits() {
        return totalPiezasKits;
    }

    public void setTotalPiezasKits(int totalPiezasKits) {
        this.totalPiezasKits = totalPiezasKits;
    }

    public int getKitsActivos() {
        return kitsActivos;
    }

    public void setKitsActivos(int kitsActivos) {
        this.kitsActivos = kitsActivos;
    }

    public int getKitsDeBaja() {
        return kitsDeBaja;
    }

    public void setKitsDeBaja(int kitsDeBaja) {
        this.kitsDeBaja = kitsDeBaja;
    }

    public int getKitsPrestados() {
        return kitsPrestados;
    }

    public void setKitsPrestados(int kitsPrestados) {
        this.kitsPrestados = kitsPrestados;
    }

    public int getKitsDisponibles() {
        return kitsDisponibles;
    }

    public void setKitsDisponibles(int kitsDisponibles) {
        this.kitsDisponibles = kitsDisponibles;
    }
}
