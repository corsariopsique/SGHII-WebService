package com.ingeniarinoxidables.sghiiwebservice.controlador;

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

    @PostMapping
    public Operacion agregar(@RequestBody Operacion operacion) { return service.guardarOperacion(operacion); }

}
