package com.ingeniarinoxidables.sghiiwebservice.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@JsonIgnoreProperties({"proveedor","items"})
public class Herramienta {
    @Id
    @Column(name= "idherramienta")
    private String id;

    @Column
    private String nombre;

    @Column
    private String categoria;

    @Column
    private String rol;

    @Column
    private String marca;

    @Column
    private LocalDate fecha_in;

    @Column
    private LocalDate fecha_out;

    @Column
    private int cantidad;

    @Column
    private int cantidad_disponible;

    @Column
    private int cantidad_kits;

    @Column
    private boolean estado;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name ="herramienta_proveedor",
            joinColumns = @JoinColumn(name="idherramienta"),
            inverseJoinColumns = @JoinColumn(name="id_prove")
    )
    private List<Proveedor> proveedor;

    @OneToMany(mappedBy = "herramienta", cascade = CascadeType.ALL)
    private List<ItemHerramienta> items;

    public Herramienta() {
    }

    public Herramienta(String id, String nombre, String categoria, String rol, String marca, LocalDate fecha_in, LocalDate fecha_out, int cantidad, int cantidad_disponible, int cantidad_kits, boolean estado, List<Proveedor> proveedor, List<ItemHerramienta> items) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.rol = rol;
        this.marca = marca;
        this.fecha_in = fecha_in;
        this.fecha_out = fecha_out;
        this.cantidad = cantidad;
        this.cantidad_disponible = cantidad_disponible;
        this.cantidad_kits = cantidad_kits;
        this.estado = estado;
        this.proveedor = proveedor;
        this.items = items;
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

    public String getCategoria() {
        return categoria;
    }

    public String getRol() {
        return rol;
    }

    public String getMarca() {
        return marca;
    }

    public LocalDate getFecha_in() {
        return fecha_in;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setFecha_in(LocalDate fecha_in) {
        this.fecha_in = fecha_in;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List<Proveedor> getProveedor() {
        return proveedor;
    }

    public void setProveedor(List<Proveedor> proveedor) {
        this.proveedor = proveedor;
    }

    public int getCantidad_disponible() {
        return cantidad_disponible;
    }

    public void setCantidad_disponible(int cantidad_disponible) {
        this.cantidad_disponible = cantidad_disponible;
    }

    public int getCantidad_kits() {
        return cantidad_kits;
    }

    public void setCantidad_kits(int cantidad_kits) {
        this.cantidad_kits = cantidad_kits;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public LocalDate getFecha_out() {
        return fecha_out;
    }

    public void setFecha_out(LocalDate fecha_out) {
        this.fecha_out = fecha_out;
    }

    public List<ItemHerramienta> getItems() {
        return items;
    }

    public void setItems(List<ItemHerramienta> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Herramienta{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", rol='" + rol + '\'' +
                ", marca='" + marca + '\'' +
                ", fecha_in=" + fecha_in +
                ", fecha_out=" + fecha_out +
                ", cantidad=" + cantidad +
                ", cantidad_disponible=" + cantidad_disponible +
                ", cantidad_kits=" + cantidad_kits +
                ", estado=" + estado +
                ", proveedor=" + proveedor +
                ", items=" + items +
                '}';
    }
}
