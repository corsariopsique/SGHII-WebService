package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.HerramientaResumenDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.HerramientaResumenPorIdDto;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Proveedor;
import com.ingeniarinoxidables.sghiiwebservice.servicio.HerramientaServicio;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Herramienta> herramientas = service.listarHerramientasPorEstado(Boolean.FALSE);
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

    @GetMapping("/{id}/operaciones")
    public ResponseEntity<List<Operacion>> listarOperaciones(@PathVariable String id){
        List<Operacion> operaciones = service.listarOperacionesTool(id);
        if(operaciones != null){
            return ResponseEntity.ok(operaciones);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/proveedores")
    public ResponseEntity<List<Proveedor>> listarProveedores (@PathVariable String id){
        List<Proveedor> proveedores = service.listarProveedores(id);
        if (proveedores != null) {
            return ResponseEntity.ok(proveedores);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/resumen")
    public ResponseEntity<HerramientaResumenDto> resumen(){
        HerramientaResumenDto resumen = service.resumen();
        return ResponseEntity.ok(resumen);
    }

    @GetMapping("/{id}/resumen")
    public ResponseEntity<HerramientaResumenPorIdDto> resumenPorId (@PathVariable String id){
        HerramientaResumenPorIdDto resumen = service.resumenPorId(id);
        return ResponseEntity.ok(resumen);
    }

    @PostMapping
    public ResponseEntity<Herramienta> agregar(@RequestBody Herramienta herramienta) {
        Herramienta herramientaNueva = service.guardarHerramienta(herramienta);
        return ResponseEntity.ok(herramientaNueva);
        }

    @PutMapping("/{id}")
    public ResponseEntity<Herramienta> actualizar (@PathVariable String id, @RequestBody Herramienta herramienta){
        Optional<Herramienta> herramientaExistente = service.obtenerHerramientaPorId(id);
        if (herramientaExistente.isPresent()) {
            herramienta.setId(id);
            herramienta.setProveedor(herramientaExistente.get().getProveedor());
            Herramienta herramientaModificada = service.guardarHerramienta(herramienta);
            return ResponseEntity.ok(herramientaModificada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Herramienta> eliminar (@PathVariable String id){
        Herramienta toolEliminada = service.eliminarHerramienta(id);
        if ( toolEliminada != null){
            return ResponseEntity.ok().body(toolEliminada);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    }
