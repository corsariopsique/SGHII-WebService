package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.servicio.BusquedaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/busqueda")

public class BusquedaControlador {

    @Autowired
    private BusquedaServicio busquedaServicio;

    @GetMapping
    public ResponseEntity<Map<String,Object>> resultadosBusqueda (@RequestParam String query){
        Map<String, Object> resultados = busquedaServicio.resultadoBusqueda(query);
        return ResponseEntity.ok(resultados);
    }

}
