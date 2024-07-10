package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListaContenedor;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
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

    @GetMapping("/prestamo/{id}")
    public ResponseEntity<ListaContenedor> enPrestamoPorId(@PathVariable String id){
        Operario operario = service.obtenerOperarioPorId(id);
        if(operario!=null){
            ListaContenedor<Herramienta, Kit> enPrestamo = service.herramientasPrestamoActivo(id);
            return ResponseEntity.ok(enPrestamo);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Operario> agregar(@RequestBody Operario operario) {
        Operario operarioNuevo = service.guardarOperario(operario);
        return ResponseEntity.ok(operarioNuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Operario> actualizar(@PathVariable String id, @RequestBody Operario operario) {
        Operario operarioExistente = service.obtenerOperarioPorId(id);
        if (operarioExistente!= null){
            operario.setId(id);
            Operario operarioModificado = service.guardarOperario(operario);
            return ResponseEntity.ok(operarioModificado);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {service.eliminarOperario(id);}

}
