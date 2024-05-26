package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Proveedor;
import com.ingeniarinoxidables.sghiiwebservice.servicio.HerramientaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/herramientas")
public class HerramientaControlador {

    @Autowired
    private HerramientaServicio service;

    @GetMapping
    public ResponseEntity<List<Herramienta>> listar() {
        List<Herramienta> herramientas = service.listarHerramientas();
        return ResponseEntity.ok(herramientas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Herramienta> obtenerPorId(@PathVariable String id) {
        Optional<Herramienta> herramienta = service.obtenerHerramientaPorId(id);
        if (herramienta.isPresent()){
            return ResponseEntity.ok(herramienta.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping (consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Herramienta agregar(@RequestBody Herramienta herramienta) {
        return service.guardarHerramienta(herramienta);
    }

    @PutMapping("/{id}")
    public Herramienta actualizar(@PathVariable String id, @RequestBody Herramienta herramienta) {
        Optional<Herramienta> herramientaExistente = service.obtenerHerramientaPorId(id);
        if (herramientaExistente.isPresent() ) {
            herramienta.setId(id);
            herramienta.setProveedor(herramientaExistente.get().getProveedor());
            return service.guardarHerramienta(herramienta);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        service.eliminarHerramienta(id);
    }

    @PostMapping("/{id}/proveedores")
    public ResponseEntity<Herramienta> addProveedor(@PathVariable String id, @RequestBody Proveedor proveedor){
        Herramienta herramienta = service.addProveedor(id,proveedor);
        if(herramienta != null){
            return ResponseEntity.ok(herramienta);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
