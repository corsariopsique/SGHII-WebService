package com.ingeniarinoxidables.sghiiwebservice.DTOs;

import java.time.LocalDate;
import java.util.List;


public class AgregarOperacion {

    private String id;

    private String operario;

    private String kit;

    private LocalDate fecha_operacion;

    private int tipo;

    private int tipo_articulo;

    private List<PaqueteHerramientasKit> herramienta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperario() {
        return operario;
    }

    public void setOperario(String operario) {
        this.operario = operario;
    }

    public String getKit() {
        return kit;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

    public LocalDate getFecha_operacion() {
        return fecha_operacion;
    }

    public void setFecha_operacion(LocalDate fecha_operacion) {
        this.fecha_operacion = fecha_operacion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo_articulo() {
        return tipo_articulo;
    }

    public void setTipo_articulo(int tipo_articulo) {
        this.tipo_articulo = tipo_articulo;
    }

    public List<PaqueteHerramientasKit> getHerramienta() {
        return herramienta;
    }

    public void setHerramienta(List<PaqueteHerramientasKit> herramienta) {
        this.herramienta = herramienta;
    }

    @Override
    public String toString() {
        return "AgregarOperacion{" +
                "id='" + id + '\'' +
                ", operario='" + operario + '\'' +
                ", kit='" + kit + '\'' +
                ", fecha_operacion=" + fecha_operacion +
                ", tipo=" + tipo +
                ", tipo_articulo=" + tipo_articulo +
                ", herramienta=" + herramienta +
                '}';
    }
}



