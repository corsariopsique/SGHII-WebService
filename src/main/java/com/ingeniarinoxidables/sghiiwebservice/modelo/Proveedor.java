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


    public Proveedor() {
    }

    public Proveedor(String id) {
        this.id = id;
    }

    public Proveedor(String id, List<Herramienta> herramientas, String nombre, String telefono, String ciudad, LocalDate fecha_in) {
        this.id = id;
        this.herramientas = herramientas;
        this.nombre = nombre;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.fecha_in = fecha_in;
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

    @Override
    public String toString() {
        return "Proveedor{" +
                "id='" + id + '\'' +
                ", herramientas=" + herramientas +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", fecha_in=" + fecha_in +
                '}';
    }
}
