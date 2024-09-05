package com.ingeniarinoxidables.sghiiwebservice.controlador;

import com.ingeniarinoxidables.sghiiwebservice.DataSets.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/data")
public class DataSetsControlador {

    @Autowired
    private DataSetsOperacionesTipo7d oper7d;

    @Autowired
    private DataSetHerramientasFrecuencia freqtools;

    @Autowired
    private DataSetRadarHerramienta radarTool;

    @Autowired
    private DataSetRadarItem radarItem;

    @Autowired
    private DataSetRadarKit radarKit;

    @Autowired
    private IndicadorAcumulativoDesgaste indicadorAcumulativoDesgaste;

    @Autowired
    private DataSetPrestamosOperario dataSetPrestamosOperario;

    @Autowired
    private PiezasDisponiblesByTool piezasDisponiblesByTool;

    @Autowired
    private HerramientasPorRolDataSet herramientasPorRolDataSet;


    @GetMapping("/oper7d")
    public ResponseEntity<String> dataPrestamos(){
        String data = oper7d.jsonData();
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/freqtools")
    public ResponseEntity<String> dataFreqTools(){
        String data = freqtools.getJson();
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/radarTool/{id}")
    public ResponseEntity<String> radarTool(@PathVariable String id){
        String data = radarTool.getJson(id);
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/radarItem/{id}")
    public ResponseEntity<String> radarItem(@PathVariable int id){
        String data = radarItem.getJson(id);
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/radarKit/{id}")
    public ResponseEntity<String> radarKit(@PathVariable String id){
        String data = radarKit.getJson(id);
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/prestamosByKit")
    public ResponseEntity<String> prestamosByKit(){
        String data = radarKit.getJsonPrestamosKits();
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/indicesTool/{id}")
    public ResponseEntity<String> indicesTool(@PathVariable String id){
        String data = indicadorAcumulativoDesgaste.getJsonTool(id);
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/indicesKit")
    public ResponseEntity<String> indicesKit(){
        String data = indicadorAcumulativoDesgaste.getJsonKit();
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/indicesAllTools")
    public ResponseEntity<String> indicesAllTools(){
        String data = indicadorAcumulativoDesgaste.getJsonAllTool();
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/prestamosToolWorker/{id}")
    public ResponseEntity<String> prestamosToolWorker(@PathVariable String id){
        String data = dataSetPrestamosOperario.getJsonWorkersTools(id);
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/prestamosKitWorker/{id}")
    public ResponseEntity<String> prestamosKitWorker(@PathVariable String id){
        String data = dataSetPrestamosOperario.getJsonWorkersKits(id);
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/prestamosWorkerTotales")
    public ResponseEntity<String> prestamosTotalesOperario(){
        String data = dataSetPrestamosOperario.getJsonWorkersPrestamosTotales();
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/workersByRole")
    public ResponseEntity<String> operariosByRol(){
        String data = dataSetPrestamosOperario.getJsonWorkersByRole();
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/disponiblesByTool")
    public ResponseEntity<String> disponiblesByTool(){
        String data = piezasDisponiblesByTool.jsonData();
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bajasByTool")
    public ResponseEntity<String> bajasByTool(){
        String data = piezasDisponiblesByTool.jsonDataBajasTool();
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/toolsByRole")
    public ResponseEntity<String> toolsByRole(){
        String data = herramientasPorRolDataSet.getJsonRole();
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/toolsByCat")
    public ResponseEntity<String> toolsByCat(){
        String data = herramientasPorRolDataSet.getJsonCat();
        if(data != null){
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(data);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
