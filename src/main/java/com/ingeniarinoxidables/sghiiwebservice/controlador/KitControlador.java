package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.KitEditar;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.PaqueteHerramientasKit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.servicio.HerramientaServicio;
import com.ingeniarinoxidables.sghiiwebservice.servicio.KitServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/kits")
public class KitControlador {

    @Autowired
    private KitServicio service;

    @Autowired
    private HerramientaServicio serviceTool;

    @GetMapping
    public ResponseEntity<List<Kit>> listar() {
        List<Kit> kits = service.listarKits();
        return ResponseEntity.ok(kits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kit> obtenerPorId(@PathVariable String id) {
        Kit kit = service.obtenerKitPorId(id);
        return ResponseEntity.ok(kit);
    }

    @PostMapping
    public ResponseEntity<Kit> agregar(@RequestBody Kit kit) {
        Kit nuevoKit = service.guardarKit(kit);
        return ResponseEntity.ok(nuevoKit);
        }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {service.eliminarKit(id);}

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
