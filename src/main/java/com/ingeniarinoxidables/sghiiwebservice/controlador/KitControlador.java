package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.PaqueteHerramientasKit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
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

    @PutMapping("/{id}")
    public ResponseEntity<Kit> actualizar(@PathVariable String id, @RequestBody Kit kit) {
        Kit kitExistente = service.obtenerKitPorId(id);
        if (kitExistente != null){
            kit.setId(id);
            kit.setHerramientas(kitExistente.getHerramientas());
            Kit kitModificado = service.guardarKit(kit);
            return ResponseEntity.ok(kitModificado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {service.eliminarKit(id);}

    @PostMapping ("/{idkit}/herramientas")
    public ResponseEntity<Kit> addHerramienta(@PathVariable String idkit, @RequestBody List<PaqueteHerramientasKit> herramientasKit){
        for(PaqueteHerramientasKit herramienta : herramientasKit){
            String id = herramienta.getId();
            int cantidad = herramienta.getCantidad();
            for(int i = 0; i<cantidad;i++){
                Herramienta toolKit = serviceTool.obtenerHerramientaPorId(id).get();
                Kit kit = service.addHerramienta(idkit,toolKit);
            }
        }
        Kit kitActualizado = service.obtenerKitPorId(idkit);
        if(kitActualizado != null){
            return ResponseEntity.ok(kitActualizado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
