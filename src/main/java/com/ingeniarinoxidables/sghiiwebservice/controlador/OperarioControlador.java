package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;
import com.ingeniarinoxidables.sghiiwebservice.servicio.OperarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/operarios")
public class OperarioControlador {

    @Autowired
    private OperarioServicio service;

    @GetMapping
    public ResponseEntity<List<Operario>> listar() {
        List<Operario> operarios = service.listarOperarios();
        return ResponseEntity.ok(operarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operario> obtenerPorId(@PathVariable String id) {
        Operario operario = service.obtenerOperarioPorId(id);
        return ResponseEntity.ok(operario);
    }

    @PostMapping
    public Operario agregar(@RequestBody Operario operario) { return service.guardarOperario(operario); }

    @PutMapping("/{id}")
    public Operario actualizar(@PathVariable String id, @RequestBody Operario operario) {
        Operario operarioExistente = service.obtenerOperarioPorId(id);
        if (operarioExistente!= null){
            operario.setId(id);
            return service.guardarOperario(operario);
        }else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {service.eliminarOperario(id);}

}
