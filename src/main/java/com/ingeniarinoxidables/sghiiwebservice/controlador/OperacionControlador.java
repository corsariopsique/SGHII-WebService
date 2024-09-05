package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.AgregarOperacion;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.OperacionesResumenDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.PaqueteHerramientasKit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.*;
import com.ingeniarinoxidables.sghiiwebservice.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/operaciones")
public class OperacionControlador {

    @Autowired
    private OperacionServicio service;
    @Autowired
    private HerramientaServicio serviceTool;

    @Autowired
    private ItemHerramientaServicio itemHerramientaServicio;

    @Autowired
    private OperarioServicio serviceWorker;

    @Autowired
    private KitServicio serviceKit;

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
                nuevaOperacion.setHerramienta(iteradorHerramientas(newToolsOper,operacion.getTipo()));
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

    private List<ItemHerramienta> iteradorHerramientas(List<PaqueteHerramientasKit> herramientasOperacion, int tipo) {
        List<ItemHerramienta> listado_Tools = new ArrayList<>();

        if(tipo == 1){
            for(PaqueteHerramientasKit herramienta : herramientasOperacion){
                String id = herramienta.getId();
                int cantidad = herramienta.getCantidad();
                for(int i = 0; i<cantidad;i++){
                    Herramienta toolOperacion = serviceTool.obtenerHerramientaPorId(id).get();
                    List<ItemHerramienta> listadoItems = toolOperacion.getItems();
                    ItemHerramienta itemTool = itemHerramientaServicio.itemParaAsignar(listadoItems);
                    itemTool.setEstado(1);
                    itemHerramientaServicio.guardarItem(itemTool);
                    listado_Tools.add(itemTool);
                }
            }
        } else if (tipo==2) {
            for(PaqueteHerramientasKit herramienta : herramientasOperacion){
                String id = herramienta.getId();
                int item = herramienta.getCantidad(); // trae la identidad del item
                Optional<ItemHerramienta> itemTool = itemHerramientaServicio.obtenerItemHerramienta(item);
                if(itemTool.isPresent()){
                    itemTool.get().setEstado(0);
                    itemHerramientaServicio.guardarItem(itemTool.get());
                    listado_Tools.add(itemTool.get());
                }
            }
        }

        return listado_Tools;
    }

}
