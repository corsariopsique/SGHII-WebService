package com.ingeniarinoxidables.sghiiwebservice.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Kit {

    @Id
    @Column(name="idkit")
    private String id;

    @Column
    private String rol;

    @Column
    private String nombre;

    @Column
    private LocalDate fecha_in;

    public Kit(String id, String rol, String nombre, LocalDate fecha_in) {
        this.id = id;
        this.rol = rol;
        this.nombre = nombre;
        this.fecha_in = fecha_in;
    }

    public Kit() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha_in() {
        return fecha_in;
    }

    public void setFecha_in(LocalDate fecha_in) {
        this.fecha_in = fecha_in;
    }

    @Override
    public String toString() {
        return "Kit{" +
                "id='" + id + '\'' +
                ", rol='" + rol + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecha_in=" + fecha_in +
                '}';
    }
}
