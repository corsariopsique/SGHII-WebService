package com.ingeniarinoxidables.sghiiwebservice.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
public class Proveedor {

    @Id
    @Column(name="id_prove")
    private String id;

    @Column
    private String nombre;

    @Column
    private String telefono;

    @Column
    private String ciudad;


    public Proveedor() {
    }

    public Proveedor(String id, String nombre, String telefono, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.ciudad = ciudad;
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

    @Override
    public String toString() {
        return "Proveedor{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}
