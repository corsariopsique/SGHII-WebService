package com.ingeniarinoxidables.sghiiwebservice.DTOs;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;

import java.util.List;

public class ItemHerramientaResumenPorIdDto {

    private Long prestamos;

    private Long devoluciones;

    private Long totalOper;

    private List<Kit> itemKits;

    private List<ListadoOperariosTopDto> itemWorkers;

    private Operario ubicacionWorker;

    private Kit ubicacionKit;

    public ItemHerramientaResumenPorIdDto() {
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

    public Long getTotalOper() {
        return totalOper;
    }

    public void setTotalOper(Long totalOper) {
        this.totalOper = totalOper;
    }

    public List<Kit> getItemKits() {
        return itemKits;
    }

    public void setItemKits(List<Kit> itemKits) {
        this.itemKits = itemKits;
    }

    public List<ListadoOperariosTopDto> getItemWorkers() {
        return itemWorkers;
    }

    public void setItemWorkers(List<ListadoOperariosTopDto> itemWorkers) {
        this.itemWorkers = itemWorkers;
    }

    public Operario getUbicacionWorker() {
        return ubicacionWorker;
    }

    public void setUbicacionWorker(Operario ubicacionWorker) {
        this.ubicacionWorker = ubicacionWorker;
    }

    public Kit getUbicacionKit() {
        return ubicacionKit;
    }

    public void setUbicacionKit(Kit ubicacionKit) {
        this.ubicacionKit = ubicacionKit;
    }
}
