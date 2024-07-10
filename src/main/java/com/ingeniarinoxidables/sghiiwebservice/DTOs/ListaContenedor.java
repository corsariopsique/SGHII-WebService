package com.ingeniarinoxidables.sghiiwebservice.DTOs;

import java.util.List;

public class ListaContenedor<T,U> {

    private List<T> herramientas;
    private List<U> kits;

    public ListaContenedor(List<T> herramientas, List<U> kits) {
        this.herramientas = herramientas;
        this.kits = kits;
    }

    public List<T> getHerramientas() {
        return herramientas;
    }

    public List<U> getKits() {
        return kits;
    }

    public void setHerramientas(List<T> herramientas) {
        this.herramientas = herramientas;
    }

    public void setKits(List<U> kits) {
        this.kits = kits;
    }
}
