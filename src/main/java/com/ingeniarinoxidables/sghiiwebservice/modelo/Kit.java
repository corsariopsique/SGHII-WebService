package com.ingeniarinoxidables.sghiiwebservice.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@JsonIgnoreProperties({"operaciones","operario","proveedor"})
public class Kit {

    @Id
    @Column(name = "idkit")
    private String id;

    // metodo a revisar por implementacion itemHerramienta
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name ="tool_Kit",
            joinColumns = @JoinColumn(name="id_kit"),
            inverseJoinColumns = @JoinColumn(name="id_tool")
    )
    private List<ItemHerramienta> herramientas;

    @ManyToMany(mappedBy = "kit", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Operacion> operaciones;

    @Column
    private String rol;

    @Column
    private String nombre;

    @Column
    private int disponible;

    @Column
    private LocalDate fecha_in;

    @Column
    private LocalDate fecha_out;

    @Column
    private Boolean estado;

    public Kit(String id, List<ItemHerramienta> herramientas, List<Operacion> operaciones, String rol, String nombre, int disponible, LocalDate fecha_in, LocalDate fecha_out, Boolean estado) {
        this.id = id;
        this.herramientas = herramientas;
        this.operaciones = operaciones;
        this.rol = rol;
        this.nombre = nombre;
        this.disponible = disponible;
        this.fecha_in = fecha_in;
        this.fecha_out = fecha_out;
        this.estado = estado;
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

    public List<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<Operacion> operaciones) {
        this.operaciones = operaciones;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
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

    public List<ItemHerramienta> getHerramientas() {
        return herramientas;
    }

    public void setHerramientas(List<ItemHerramienta> herramientas) {
        this.herramientas = herramientas;
    }

    @Override
    public String toString() {
        return "Kit{" +
                "id='" + id + '\'' +
                ", herramientas=" + herramientas +
                ", operaciones=" + operaciones +
                ", rol='" + rol + '\'' +
                ", nombre='" + nombre + '\'' +
                ", disponible=" + disponible +
                ", fecha_in=" + fecha_in +
                ", fecha_out=" + fecha_out +
                ", estado=" + estado +
                '}';
    }
}
