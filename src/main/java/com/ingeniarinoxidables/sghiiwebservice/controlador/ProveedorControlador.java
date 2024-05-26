package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Proveedor;
import com.ingeniarinoxidables.sghiiwebservice.servicio.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Proveedor> obtenerPorId(@PathVariable String id){
        Optional<Proveedor> proveedor = service.obtenerProveedorPorId(id);
        if(proveedor.isPresent()){
            return ResponseEntity.ok(proveedor.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Proveedor agregar(@RequestBody Proveedor proveedor) { return service.guardarProveedor(proveedor); }

    @PutMapping("/{id}")
    public Proveedor actualizar(@PathVariable String id, @RequestBody Proveedor proveedor) {
        Optional<Proveedor> proveedorExistente = service.obtenerProveedorPorId(id);
        if(proveedorExistente.isPresent()){
            proveedor.setId(id);
            return service.guardarProveedor(proveedor);
        }else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) { service.eliminarProveedor(id); }

}
