package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Proveedor;
import com.ingeniarinoxidables.sghiiwebservice.servicio.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio service;

    @GetMapping
    public ResponseEntity<List<Proveedor>> listar() {
        List<Proveedor> proveedores = service.listarProveedores();
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{id}")
    public Proveedor obtenerPotId(@PathVariable String id) { return service.obtenerProveedorPorId(id); }

    @PostMapping
    public Proveedor agregar(@RequestBody Proveedor proveedor) { return service.guardarProveedor(proveedor); }

    @PutMapping("/{id}")
    public Proveedor actualizar(@PathVariable String id, @RequestBody Proveedor proveedor) {
        Proveedor proveedorExistente = service.obtenerProveedorPorId(id);
        if(proveedorExistente!= null){
            proveedor.setId(id);
            return service.guardarProveedor(proveedor);
        }else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) { service.eliminarProveedor(id); }

}
