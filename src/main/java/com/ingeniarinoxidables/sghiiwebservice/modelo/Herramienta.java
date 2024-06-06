package com.ingeniarinoxidables.sghiiwebservice.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Entity
@JsonIgnoreProperties({"proveedor","kits","operaciones"})
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
    private int cantidad;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name ="herramienta_proveedor",
            joinColumns = @JoinColumn(name="idherramienta"),
            inverseJoinColumns = @JoinColumn(name="id_prove")
    )
    private List<Proveedor> proveedor;

    @ManyToMany(mappedBy = "herramientas",fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Kit> kits;

    @OneToMany(mappedBy = "herramienta", cascade = CascadeType.ALL)
    private List<Operacion> operaciones;

    @Column
    private byte[] image;

    public Herramienta() {
    }

    public Herramienta(String id, String nombre, String categoria, String rol, String marca, LocalDate fecha_in, int cantidad, List<Proveedor> proveedor, List<Kit> kits, List<Operacion> operaciones, byte[] image) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.rol = rol;
        this.marca = marca;
        this.fecha_in = fecha_in;
        this.cantidad = cantidad;
        this.proveedor = proveedor;
        this.kits = kits;
        this.operaciones = operaciones;
        this.image = image;
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

    public byte[] getImage() {
        return image;
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

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<Proveedor> getProveedor() {
        return proveedor;
    }

    public void setProveedor(List<Proveedor> proveedor) {
        this.proveedor = proveedor;
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

    @Override
    public String toString() {
        return "Herramienta{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", rol='" + rol + '\'' +
                ", marca='" + marca + '\'' +
                ", fecha_in=" + fecha_in +
                ", cantidad=" + cantidad +
                ", proveedor=" + proveedor +
                ", kits=" + kits +
                ", operaciones=" + operaciones +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}
