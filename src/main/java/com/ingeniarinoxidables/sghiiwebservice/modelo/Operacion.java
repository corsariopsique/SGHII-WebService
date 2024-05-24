package com.ingeniarinoxidables.sghiiwebservice.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity(name="asignacion-devolucion")
public class Operacion {

    @Id
    @Column(name="id_operaciones")
    private String id;

    @Column
    private String id_trabajador;

    @Column
    private int tipo;

    @Column
    private LocalDate fecha_operacion;

    public Operacion() {
    }

    public Operacion(String id, String id_trabajador, int tipo, LocalDate fecha_operacion) {
        this.id = id;
        this.id_trabajador = id_trabajador;
        this.tipo = tipo;
        this.fecha_operacion = fecha_operacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(String id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha_operacion() {
        return fecha_operacion;
    }

    public void setFecha_operacion(LocalDate fecha_operacion) {
        this.fecha_operacion = fecha_operacion;
    }

    @Override
    public String toString() {
        return "Operacion{" +
                "id='" + id + '\'' +
                ", id_trabajador='" + id_trabajador + '\'' +
                ", tipo=" + tipo +
                ", fecha_operacion=" + fecha_operacion +
                '}';
    }
}
