package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.AgregarOperacion;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.OperacionesResumenDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.PaqueteHerramientasKit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;
import com.ingeniarinoxidables.sghiiwebservice.servicio.*;
import com.ingeniarinoxidables.sghiiwebservice.servicio.DataSets.DataSetHerramientasFrecuencia;
import com.ingeniarinoxidables.sghiiwebservice.servicio.DataSets.DataSetsOperacionesTipo7d;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/operaciones")
public class OperacionControlador {

    @Autowired
    private OperacionServicio service;
    @Autowired
    private HerramientaServicio serviceTool;

    @Autowired
    private OperarioServicio serviceWorker;

    @Autowired
    private KitServicio serviceKit;

    @Autowired
    private DataSetsOperacionesTipo7d servicioDataSet;

    @Autowired
    private DataSetHerramientasFrecuencia servicioDataSetFreqTools;

    @GetMapping
    public ResponseEntity<List<Operacion>> listar() {
        List<Operacion> operaciones = service.listarOperaciones();
        return ResponseEntity.ok(operaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operacion> obtenerPorId(@PathVariable String id) {
        Operacion operacion = service.obtenerOperacionPorId(id);
        return ResponseEntity.ok(operacion);
    }

    @GetMapping("/resumen")
    public ResponseEntity<OperacionesResumenDto> resumen(){
        OperacionesResumenDto resumen = service.resumen();
        return ResponseEntity.ok(resumen);
    }

    @GetMapping("/data/oper7d")
    public ResponseEntity<String> dataPrestamos(){
        String data = servicioDataSet.jsonData();
        return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(data);
    }

    @GetMapping("/data/freqtools")
    public ResponseEntity<String> dataFreqTools(){
        String data = servicioDataSetFreqTools.getJson();
        return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(data);
    }

    @PostMapping
    public ResponseEntity<Operacion> agregar(@RequestBody AgregarOperacion operacion) {
        Operario operarioExistente = serviceWorker.obtenerOperarioPorId(operacion.getOperario());
        if(operarioExistente != null){
            Operacion nuevaOperacion = new Operacion();
            nuevaOperacion.setId(operacion.getId());
            nuevaOperacion.setOperario(operarioExistente);
            nuevaOperacion.setFecha_operacion(operacion.getFecha_operacion());
            nuevaOperacion.setTipo(operacion.getTipo());
            if(operacion.getTipo_articulo()==1){
                List<PaqueteHerramientasKit> newToolsOper = operacion.getHerramienta();
                nuevaOperacion.setHerramienta(iteradorHerramientas(newToolsOper));
            }
            if(operacion.getTipo_articulo()==2){
                List<Kit> kits_Oper = new ArrayList<>();
                Kit kit_oper = serviceKit.obtenerKitPorId(operacion.getKit());
                kits_Oper.add(kit_oper);
                nuevaOperacion.setKit(kits_Oper);
            }
            Operacion operacionCompleta = service.guardarOperacion(operacion.getOperario(),nuevaOperacion);

            return ResponseEntity.ok(operacionCompleta);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    private List<Herramienta> iteradorHerramientas(List<PaqueteHerramientasKit> herramientasOperacion) {
        List<Herramienta> listado_Tools = new ArrayList<>();
        for(PaqueteHerramientasKit herramienta : herramientasOperacion){
            String id = herramienta.getId();
            int cantidad = herramienta.getCantidad();
            for(int i = 0; i<cantidad;i++){
                Herramienta toolOperacion = serviceTool.obtenerHerramientaPorId(id).get();
                listado_Tools.add(toolOperacion);
            }
        }
        return listado_Tools;
    }

}
