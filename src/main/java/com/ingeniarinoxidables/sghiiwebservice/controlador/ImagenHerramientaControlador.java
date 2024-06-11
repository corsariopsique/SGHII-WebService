package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.modelo.ImagenHerramienta;
import com.ingeniarinoxidables.sghiiwebservice.servicio.ImagenHerramientaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImagenHerramientaControlador {

    @Autowired
    private ImagenHerramientaServicio service;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> obtenerPorId(@PathVariable String id){
        Optional<ImagenHerramienta> imagenHerramienta = service.obtenerImagenHPorId(id);
        if (imagenHerramienta.isPresent()){
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imagenHerramienta.get().getImage_name() + "\"")
                    .body(imagenHerramienta.get().getImage());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ImagenHerramienta> agregar(@RequestBody ImagenHerramienta imagenHerramienta){
        ImagenHerramienta imagenHNueva = service.guardarImagenH(imagenHerramienta);
        return ResponseEntity.ok(imagenHNueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<byte[]> editarImagenh(@PathVariable String id, @RequestBody byte[] imagenHerramienta){
        Optional<ImagenHerramienta> imagenHExistente = service.obtenerImagenHPorId(id);
        if(imagenHExistente.isPresent()){
            if(!imagenHExistente.get().isValidBase64(imagenHerramienta)){
                return ResponseEntity.badRequest().body(imagenHerramienta);
            }
            imagenHExistente.get().setImage(imagenHerramienta);
            ImagenHerramienta imagenActualizada = service.guardarImagenH(imagenHExistente.get());
            return ResponseEntity.ok(imagenActualizada.getImage());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void eliminarImagen (@PathVariable String id){
        service.eliminarImagenH(id);
    }


}
