package com.ingeniarinoxidables.sghiiwebservice.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity(name="asignacion-devolucion")
public class Operacion {

    @Id
    @Column(name="id_operaciones")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_trabajador",nullable = false)
    @JsonBackReference
    private Operario operario;

    @Column
    private int tipo;

    @Column
    private LocalDate fecha_operacion;

    public Operacion() {
    }

    public Operacion(String id, Operario operario, int tipo, LocalDate fecha_operacion) {
        this.id = id;
        this.operario = operario;
        this.tipo = tipo;
        this.fecha_operacion = fecha_operacion;
    }

    public Operacion(String id, int tipo, LocalDate fecha_operacion) {
        this.id = id;
        this.tipo = tipo;
        this.fecha_operacion = fecha_operacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Operario getOperario() {
        return operario;
    }

    public void setOperario(Operario operario) {
        this.operario = operario;
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
                ", operario=" + operario +
                ", tipo=" + tipo +
                ", fecha_operacion=" + fecha_operacion +
                '}';
    }
}
