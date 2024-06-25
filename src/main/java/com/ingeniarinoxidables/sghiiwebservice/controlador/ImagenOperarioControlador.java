package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.modelo.ImagenOperario;
import com.ingeniarinoxidables.sghiiwebservice.servicio.ImagenOperarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/imagesworker")
public class ImagenOperarioControlador {

    @Autowired
    private ImagenOperarioServicio service;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> obtenerPorId(@PathVariable String id){
        Optional<ImagenOperario> imagenOperario = service.obtenerImagenOPorId(id);
        if(imagenOperario.isPresent()){
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imagenOperario.get().getImage_name() + "\"")
                    .body(imagenOperario.get().getImage());
        }else{
            return  ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ImagenOperario> agregarImagen(@RequestBody ImagenOperario imagenOperario){
        ImagenOperario imagenONueva = service.guardarImagenO(imagenOperario);
        return ResponseEntity.ok(imagenONueva);
    }

    @PutMapping("{id}")
    public ResponseEntity<byte[]> editarImagenO (@PathVariable String id, @RequestBody byte[] imagenOperario){
        Optional<ImagenOperario> imagenOExistente = service.obtenerImagenOPorId(id);
        if(imagenOExistente.isPresent()){
            if(!imagenOExistente.get().isValidBase64(imagenOperario)){
                return ResponseEntity.badRequest().body(imagenOperario);
            }
            imagenOExistente.get().setImage(imagenOperario);
            ImagenOperario imagenActualizada = service.guardarImagenO(imagenOExistente.get());
            return ResponseEntity.ok(imagenActualizada.getImage());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void eliminarImagen (@PathVariable String id) { service.eliminarImagenO(id); }

}
