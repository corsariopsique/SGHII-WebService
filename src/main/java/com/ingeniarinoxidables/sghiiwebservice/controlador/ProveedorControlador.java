package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.ProveedorDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.Tool;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Proveedor;
import com.ingeniarinoxidables.sghiiwebservice.servicio.HerramientaServicio;
import com.ingeniarinoxidables.sghiiwebservice.servicio.ProveedorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio service;

    @Autowired
    private HerramientaServicio herramientaServicio;

    @GetMapping
    public ResponseEntity<List<Proveedor>> listar() {
        List<Proveedor> proveedores = service.listarProveedoresPorEstado(Boolean.FALSE);
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtenerPorId(@PathVariable String id){
        Optional<Proveedor> proveedor = service.obtenerProveedorPorId(id);
        if(proveedor.isPresent()){
            return ResponseEntity.ok(proveedor.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Proveedor> agregar(@RequestBody ProveedorDto proveedor) {
        Proveedor proveedorNuevo = new Proveedor();
        proveedorNuevo.setId(proveedor.getId());
        proveedorNuevo.setNombre(proveedor.getNombre());
        proveedorNuevo.setCiudad(proveedor.getCiudad());
        proveedorNuevo.setTelefono(proveedor.getTelefono());
        proveedorNuevo.setFecha_in(proveedor.getFecha_in());
        proveedorNuevo.setEstado(proveedor.getEstado());
        Proveedor proveedorFinal = service.guardarProveedor(proveedorNuevo);
        for(Tool tool : proveedor.getHerramientas()){
            Optional herramienta = herramientaServicio.obtenerHerramientaPorId(tool.getId());
            if(herramienta.isPresent()){
                herramientaServicio.addProveedor(tool.getId(),proveedorFinal);
            }
        }
        return ResponseEntity.ok(proveedorFinal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable String id, @RequestBody ProveedorDto proveedor) {
        Optional<Proveedor> proveedorExistente = service.obtenerProveedorPorId(id);
        if(proveedorExistente.isPresent()){
            Proveedor proveedorActualizado = proveedorExistente.get();
            proveedorActualizado.setNombre(proveedor.getNombre());
            proveedorActualizado.setTelefono(proveedor.getTelefono());
            proveedorActualizado.setCiudad(proveedor.getCiudad());
            for(Herramienta tool : proveedorActualizado.getHerramientas()){
                herramientaServicio.dropSuplier(tool.getId(),proveedorActualizado);
            }
            service.guardarProveedor(proveedorActualizado);
            for(Tool tool : proveedor.getHerramientas()){
                Optional herramienta = herramientaServicio.obtenerHerramientaPorId(tool.getId());
                if(herramienta.isPresent()){
                    herramientaServicio.addProveedor(tool.getId(),proveedorActualizado);
                }
            }
            return ResponseEntity.ok(proveedorActualizado);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Proveedor> eliminar(@PathVariable String id) {
        Proveedor suplierEliminado = service.eliminarProveedor(id);
        if(suplierEliminado != null){
            return ResponseEntity.ok().body(suplierEliminado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
