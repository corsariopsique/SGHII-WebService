package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListaContenedor;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.OperarioResumenDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.OperarioResumenPorIdDto;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
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
        List<Operario> operarios = service.listarOperariosPorEstado(Boolean.FALSE);
        return ResponseEntity.ok(operarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operario> obtenerPorId(@PathVariable String id) {
        Operario operario = service.obtenerOperarioPorId(id);
        return ResponseEntity.ok(operario);
    }

    @GetMapping("/{id}/prestamo")
    public ResponseEntity<ListaContenedor> enPrestamoPorId(@PathVariable String id){
        Operario operario = service.obtenerOperarioPorId(id);
        if(operario!=null){
            ListaContenedor<Herramienta, Kit> enPrestamo = service.herramientasPrestamoActivo(id);
            return ResponseEntity.ok(enPrestamo);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/operaciones")
    public ResponseEntity<List<Operacion>> operacionesWorker(@PathVariable String id){
        List<Operacion> operaciones = service.operacionesWorker(id);
        if(operaciones != null){
            return ResponseEntity.ok(operaciones);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/resumen")
    public ResponseEntity<OperarioResumenDto> resumen(){
        OperarioResumenDto resumen = service.resumen();
        return ResponseEntity.ok(resumen);
    }

    @GetMapping("/{id}/resumen")
    public ResponseEntity<OperarioResumenPorIdDto> resumenPorID (@PathVariable String id){
        OperarioResumenPorIdDto resumenPorIdDto = service.resumenPorID(id);
        return ResponseEntity.ok(resumenPorIdDto);
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
    public ResponseEntity<Operario> eliminar(@PathVariable String id) {
        Operario operarioEliminado = service.eliminarOperario(id);
        if(operarioEliminado != null){
            return ResponseEntity.ok(operarioEliminado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
