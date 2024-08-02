package com.ingeniarinoxidables.sghiiwebservice.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Proveedor {

    @Id
    @Column(name="id_prove")
    private String id;

    @ManyToMany(mappedBy = "proveedor",fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Herramienta> herramientas;

    @Column
    private String nombre;

    @Column
    private String telefono;

    @Column
    private String ciudad;

    @Column
    private LocalDate fecha_in;

    @Column
    private LocalDate fecha_out;

    @Column
    private Boolean estado;


    public Proveedor() {
    }

    public Proveedor(String id) {
        this.id = id;
    }

    public Proveedor(String id, List<Herramienta> herramientas, String nombre, String telefono, String ciudad, LocalDate fecha_in, LocalDate fecha_out, Boolean estado) {
        this.id = id;
        this.herramientas = herramientas;
        this.nombre = nombre;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.fecha_in = fecha_in;
        this.fecha_out = fecha_out;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Herramienta> getHerramientas() {
        return herramientas;
    }

    public void setHerramientas(List<Herramienta> herramientas) {
        this.herramientas = herramientas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDate getFecha_in() {
        return fecha_in;
    }

    public void setFecha_in(LocalDate fecha_in) {
        this.fecha_in = fecha_in;
    }

    public LocalDate getFecha_out() {
        return fecha_out;
    }

    public void setFecha_out(LocalDate fecha_out) {
        this.fecha_out = fecha_out;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "id='" + id + '\'' +
                ", herramientas=" + herramientas +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", fecha_in=" + fecha_in +
                ", fecha_out=" + fecha_out +
                ", estado=" + estado +
                '}';
    }
}
