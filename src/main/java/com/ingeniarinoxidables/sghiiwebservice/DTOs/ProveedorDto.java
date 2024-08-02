package com.ingeniarinoxidables.sghiiwebservice.DTOs;

import java.time.LocalDate;
import java.util.List;

public class ProveedorDto {

    private String id;

    private String nombre;

    private String ciudad;

    private String telefono;

    private LocalDate fecha_in;

    private List<Tool> herramientas;

    private Boolean estado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFecha_in() {
        return fecha_in;
    }

    public void setFecha_in(LocalDate fecha_in) {
        this.fecha_in = fecha_in;
    }

    public List<Tool> getHerramientas() {
        return herramientas;
    }

    public void setHerramientas(List<Tool> herramientas) {
        this.herramientas = herramientas;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ProveedorDto{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fecha_in=" + fecha_in +
                ", herramientas=" + herramientas +
                ", estado=" + estado +
                '}';
    }
}


