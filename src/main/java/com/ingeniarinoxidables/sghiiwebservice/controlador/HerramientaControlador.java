package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.servicio.HerramientaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Herramienta obtenerPorId(@PathVariable String id) {
        return service.obtenerHerramientaPorId(id);
    }

    @PostMapping
    public Herramienta agregar(@RequestBody Herramienta herramienta) {
        return service.guardarHerramienta(herramienta);
    }

    @PutMapping("/{id}")
    public Herramienta actualizar(@PathVariable String id, @RequestBody Herramienta herramienta) {
        Herramienta herramientaExistente = service.obtenerHerramientaPorId(id);
        if (herramientaExistente != null) {
            herramienta.setId(id);
            return service.guardarHerramienta(herramienta);
        } else {
            return null; // Manejar este caso apropiadamente
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        service.eliminarHerramienta(id);
    }


}
