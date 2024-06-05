package com.ingeniarinoxidables.sghiiwebservice.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@JsonIgnoreProperties({"herramientas"})
public class Kit {

    @Id
    @Column(name = "idkit")
    private String id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name ="tool_Kit",
            joinColumns = @JoinColumn(name="id_kit"),
            inverseJoinColumns = @JoinColumn(name="id_tool")
    )
    private List<Herramienta> herramientas;

    @Column
    private String rol;

    @Column
    private String nombre;

    @Column
    private LocalDate fecha_in;

    public Kit(String id, List<Herramienta> herramientas, String rol, String nombre, LocalDate fecha_in) {
        this.id = id;
        this.herramientas = herramientas;
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

    public List<Herramienta> getHerramientas() {
        return herramientas;
    }

    public void setHerramientas(List<Herramienta> herramientas) {
        this.herramientas = herramientas;
    }

    @Override
    public String toString() {
        return "Kit{" +
                "id='" + id + '\'' +
                ", herramientas=" + herramientas +
                ", rol='" + rol + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fecha_in=" + fecha_in +
                '}';
    }
}
