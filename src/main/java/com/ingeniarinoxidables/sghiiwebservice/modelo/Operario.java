package com.ingeniarinoxidables.sghiiwebservice.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@JsonIgnoreProperties({"operaciones"})
public class Operario {
    @Id
    @Column(name="id_worker")
    private String id;

    @OneToMany(mappedBy = "operario", cascade = CascadeType.ALL)
    private List<Operacion> operaciones;

    @Column
    private String nombre;

    @Column
    private String rol;

    @Column
    private String telefono;

    @Column
    private String email;

    @Column
    private LocalDate fecha_in;

    @Column
    private LocalDate fecha_out;

    @Column
    private Boolean estado;

    public Operario() {
    }

    public Operario(String id) {
        this.id = id;
    }

    public Operario(String id, List<Operacion> operaciones, String nombre, String rol, String telefono, String email, LocalDate fecha_in, LocalDate fecha_out, Boolean estado) {
        this.id = id;
        this.operaciones = operaciones;
        this.nombre = nombre;
        this.rol = rol;
        this.telefono = telefono;
        this.email = email;
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

    public List<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<Operacion> operaciones) {
        this.operaciones = operaciones;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Operario{" +
                "id='" + id + '\'' +
                ", operaciones=" + operaciones +
                ", nombre='" + nombre + '\'' +
                ", rol='" + rol + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", fecha_in=" + fecha_in +
                ", fecha_out=" + fecha_out +
                ", estado=" + estado +
                '}';
    }
}
