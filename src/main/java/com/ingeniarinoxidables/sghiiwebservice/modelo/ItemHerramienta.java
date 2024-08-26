package com.ingeniarinoxidables.sghiiwebservice.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name="itemherramienta")
@JsonIgnoreProperties({"kits","operaciones"})
public class ItemHerramienta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private int estado;

    @Column
    private LocalDate fecha_in;

    @Column
    private LocalDate fecha_out;

    // metodo a revisar por implementacion itemHerramienta
    @ManyToMany(mappedBy = "herramientas",fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Kit> kits;

    // metodo a revisar por implementacion itemHerramienta
    @ManyToMany (mappedBy = "herramienta", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Operacion> operaciones;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idherramienta",nullable = false)
    private Herramienta herramienta;

    public ItemHerramienta() {
    }

    public ItemHerramienta(int id, int estado, LocalDate fecha_in, LocalDate fecha_out, List<Kit> kits, List<Operacion> operaciones, Herramienta herramienta) {
        this.id = id;
        this.estado = estado;
        this.fecha_in = fecha_in;
        this.fecha_out = fecha_out;
        this.kits = kits;
        this.operaciones = operaciones;
        this.herramienta = herramienta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
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

    public List<Kit> getKits() {
        return kits;
    }

    public void setKits(List<Kit> kits) {
        this.kits = kits;
    }

    public List<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<Operacion> operaciones) {
        this.operaciones = operaciones;
    }

    public Herramienta getHerramienta() {
        return herramienta;
    }

    public void setHerramienta(Herramienta herramienta) {
        this.herramienta = herramienta;
    }

    @Override
    public String toString() {
        return "ItemHerramienta{" +
                "id=" + id +
                ", estado=" + estado +
                ", fecha_in=" + fecha_in +
                ", fecha_out=" + fecha_out +
                ", kits=" + kits +
                ", operaciones=" + operaciones +
                ", herramienta=" + herramienta +
                '}';
    }
}
