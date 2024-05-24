package com.ingeniarinoxidables.sghiiwebservice.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Operario {
    @Id
    @Column(name="id_worker")
    private String id;

    @Column
    private String nombre;

    @Column
    private String rol;

    @Column
    private LocalDate fecha_in;

    public Operario() {
    }

    public Operario(String id, String nombre, String rol, LocalDate fecha_in) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.fecha_in = fecha_in;
    }

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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public LocalDate getFecha_in() {
        return fecha_in;
    }

    public void setFecha_in(LocalDate fecha_in) {
        this.fecha_in = fecha_in;
    }

    @Override
    public String toString() {
        return "Operario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", rol='" + rol + '\'' +
                ", fecha_in=" + fecha_in +
                '}';
    }
}
