package com.ingeniarinoxidables.sghiiwebservice.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity(name="asignacion_devolucion")
@JsonIgnoreProperties({"operario"})
public class Operacion {

    @Id
    @Column(name="id_operaciones")
    private String id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_trabajador",nullable = false)
    private Operario operario;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name ="asg_dev_tool",
            joinColumns = @JoinColumn(name="id_operacion_tool",nullable = true),
            inverseJoinColumns = @JoinColumn(name="id_tool")
    )
    private Herramienta herramienta;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name ="asg_dev_kit",
            joinColumns = @JoinColumn(name="id_operacion_kit",nullable = true),
            inverseJoinColumns = @JoinColumn(name="id_kit")
    )
    private Kit kit;

    @Column
    private int tipo;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha_operacion;

    public Operacion() {
    }

    public Operacion(String id, Operario operario, Herramienta herramienta, Kit kit, int tipo, LocalDate fecha_operacion) {
        this.id = id;
        this.operario = operario;
        this.herramienta = herramienta;
        this.kit = kit;
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

    public Herramienta getHerramienta() {
        return herramienta;
    }

    public void setHerramienta(Herramienta herramienta) {
        this.herramienta = herramienta;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
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
                ", herramienta=" + herramienta +
                ", kit=" + kit +
                ", tipo=" + tipo +
                ", fecha_operacion=" + fecha_operacion +
                '}';
    }
}
