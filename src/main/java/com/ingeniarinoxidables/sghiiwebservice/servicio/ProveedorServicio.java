package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Proveedor;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.ProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ProveedorServicio {

    @Autowired
    private ProveedorRepositorio repositorio;

    public List<Proveedor> listarProveedores() { return repositorio.findAll(); }

    public List<Proveedor> listarProveedoresPorEstado(Boolean estado){
        return repositorio.findByEstado(estado);
    }

    public Optional<Proveedor> obtenerProveedorPorId(String id) { return repositorio.findById(id);}


    public Proveedor guardarProveedor(Proveedor proveedor) { return repositorio.save(proveedor); }

    public Proveedor eliminarProveedor(String id) {
        Optional<Proveedor> proveedorDeBaja = repositorio.findById(id);
        if(proveedorDeBaja.isPresent()){
            proveedorDeBaja.get().setEstado(true);
            proveedorDeBaja.get().setFecha_out(LocalDate.now());
            return repositorio.save(proveedorDeBaja.get());
        }
        return null;
    }

}
