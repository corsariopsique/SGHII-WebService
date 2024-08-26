package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.ItemHerramientaResumenPorIdDto;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.ItemHerramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.HerramientaRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.servicio.ItemHerramientaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemHerramientaControlador {

    @Autowired
    private ItemHerramientaServicio servicio;

    @Autowired
    private HerramientaRepositorio herramientaRepositorio;

    @GetMapping
    public ResponseEntity<List<ItemHerramienta>> listar() {
        List<ItemHerramienta> items = servicio.listarItemsHerramientas();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemHerramienta> obtenerPorId(@PathVariable int id) {
        Optional<ItemHerramienta> item = servicio.obtenerItemHerramienta(id);
        if (item.isPresent()){
            return ResponseEntity.ok(item.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<ItemHerramienta>> itemsByState(@PathVariable int state) {
        if(state == 0 || state == 1){
            List<ItemHerramienta> items = servicio.listarItemsHerramientasByEstado(state);
            return ResponseEntity.ok(items);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tool/{idTool}")
    public ResponseEntity<List<ItemHerramienta>> itemsByTool (@PathVariable String idTool) {
        Optional<Herramienta> tool = herramientaRepositorio.findById(idTool);
        if(tool.isPresent()){
            List<ItemHerramienta> items = servicio.listarItemsByTool(idTool);
            return ResponseEntity.ok(items);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/operaciones")
    public ResponseEntity<List<Operacion>> listarOperaciones(@PathVariable int id) {
        List<Operacion> operacionesItem = servicio.listarOperacionesItem(id);
        if(operacionesItem != null){
            return ResponseEntity.ok(operacionesItem);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/resumen")
    public ResponseEntity<ItemHerramientaResumenPorIdDto> resumenPorId (@PathVariable int id) {
        ItemHerramientaResumenPorIdDto resumen = servicio.resumenPorId(id);
        if(resumen != null){
            return ResponseEntity.ok(resumen);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ItemHerramienta> eliminarItem(@PathVariable int id) {
        ItemHerramienta itemEliminado = servicio.eliminarItemHerramienta(id);
        if(itemEliminado != null){
            return ResponseEntity.ok(itemEliminado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
