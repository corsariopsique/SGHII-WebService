package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.KitEditar;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.KitResumenDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.KitResumenPorIdDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.PaqueteHerramientasKit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.servicio.HerramientaServicio;
import com.ingeniarinoxidables.sghiiwebservice.servicio.KitServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/kits")
public class KitControlador {

    @Autowired
    private KitServicio service;

    @Autowired
    private HerramientaServicio serviceTool;

    @GetMapping
    public ResponseEntity<List<Kit>> listar() {
        List<Kit> kits = service.listarKitsPorEstado(Boolean.FALSE);
        return ResponseEntity.ok(kits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kit> obtenerPorId(@PathVariable String id) {
        Kit kit = service.obtenerKitPorId(id);
        return ResponseEntity.ok(kit);
    }

    @GetMapping("/{id}/operaciones")
    public ResponseEntity<List<Operacion>> listarOperaciones(@PathVariable String id){
        List<Operacion> operaciones = service.listarOperacionesKits(id);
        if(operaciones != null){
            return ResponseEntity.ok(operaciones);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/resumen")
    public ResponseEntity<KitResumenDto> resumen (){
        KitResumenDto resumen = service.resumen();
        return ResponseEntity.ok(resumen);
    }

    @GetMapping("{id}/resumen")
    public ResponseEntity<KitResumenPorIdDto> resumenPorId (@PathVariable String id){
        KitResumenPorIdDto resumen = service.resumenPorId(id);
        return ResponseEntity.ok(resumen);
    }

    @PostMapping
    public ResponseEntity<Kit> agregar(@RequestBody Kit kit) {
        Kit nuevoKit = service.guardarKit(kit);
        return ResponseEntity.ok(nuevoKit);
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Kit> eliminar(@PathVariable String id) {
        Kit kitEliminado = service.eliminarKit(id);
        if(kitEliminado != null){
            return ResponseEntity.ok(kitEliminado);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping ("/{idkit}/herramientas")
    public ResponseEntity<Kit> addHerramienta(@PathVariable String idkit, @RequestBody List<PaqueteHerramientasKit> herramientasKit){
        Kit kitSinTools = service.obtenerKitPorId(idkit);
        if(kitSinTools != null){
            iteradorHerramientas(idkit, herramientasKit);
            return ResponseEntity.ok(kitSinTools);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    private void iteradorHerramientas(@PathVariable String idkit, @RequestBody List<PaqueteHerramientasKit> herramientasKit) {
        for(PaqueteHerramientasKit herramienta : herramientasKit){
            String id = herramienta.getId();
            int cantidad = herramienta.getCantidad();
            for(int i = 0; i<cantidad;i++){
                Herramienta toolKit = serviceTool.obtenerHerramientaPorId(id).get();
                Kit kit = service.addHerramienta(idkit,toolKit);
            }
        }
    }

    @PutMapping ("/{idkit}")
    public ResponseEntity<Kit> editarKit(@PathVariable String idkit, @RequestBody KitEditar kitModificado){
        Kit kitExistente = service.obtenerKitPorId(idkit);
        if(kitExistente!= null){
            String nombre = kitModificado.getNombre();
            String rol = kitModificado.getRol();
            kitExistente = service.deleteHerramientas(idkit);
            List<PaqueteHerramientasKit> newTools = kitModificado.getHerramientas();
            iteradorHerramientas(idkit, newTools);
            kitExistente = service.obtenerKitPorId(idkit);
            kitExistente.setNombre(nombre);
            kitExistente.setRol(rol);
            Kit kitActualizado = service.guardarKit(kitExistente);
            return ResponseEntity.ok(kitActualizado);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

}
