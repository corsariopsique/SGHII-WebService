package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.servicio.OperacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/operaciones")
public class OperacionControlador {

    @Autowired
    private OperacionServicio service;

    @GetMapping
    public ResponseEntity<List<Operacion>> listar() {
        List<Operacion> operaciones = service.listarOperaciones();
        return ResponseEntity.ok(operaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operacion> obtenerPorId(@PathVariable String id) {
        Operacion operacion = service.obtenerOperacionPorId(id);
        return ResponseEntity.ok(operacion);
    }

    @PostMapping("/{idOperador}")
    public ResponseEntity<Operacion> agregar(@PathVariable String idOperador, @RequestBody Operacion operacion) {
        Operacion nuevaOperacion = service.guardarOperacion(idOperador,operacion);
        return ResponseEntity.ok(nuevaOperacion);
    }

    @PostMapping ("/{idOperacion}/herramientas")
    public ResponseEntity<Operacion> vincularTool(@PathVariable String idOperacion, @RequestBody Herramienta herramienta){
        Operacion operTool = service.addTool(idOperacion,herramienta);
        return ResponseEntity.ok(operTool);
    }

    @PostMapping("/{idOperacion}/kits")
    public ResponseEntity<Operacion> vincularKit(@PathVariable String idOperacion, @RequestBody Kit kit){
        Operacion operKit = service.addKit(idOperacion,kit);
        return  ResponseEntity.ok(operKit);
    }

}
