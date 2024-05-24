package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Proveedor;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.ProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorServicio {

    @Autowired
    private ProveedorRepositorio repositorio;

    public List<Proveedor> listarProveedores() { return repositorio.findAll(); }

    public Proveedor obtenerProveedorPorId(String id) { return repositorio.findById(id).orElse(null); }

    public Proveedor guardarProveedor(Proveedor proveedor) { return repositorio.save(proveedor); }

    public void eliminarProveedor(String id) { repositorio.deleteById(id); }

}
