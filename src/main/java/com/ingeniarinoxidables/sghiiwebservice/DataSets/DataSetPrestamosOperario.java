package com.ingeniarinoxidables.sghiiwebservice.DataSets;

import com.google.gson.Gson;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;
import com.ingeniarinoxidables.sghiiwebservice.servicio.OperarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DataSetPrestamosOperario {

    @Autowired
    private OperarioServicio operarioServicio;

    private final MetodosDataSets metodos = new MetodosDataSets();

    private final ColorGeneratorByPairs coloresPares = new ColorGeneratorByPairs();


    public String getJsonWorkersTools(String id){
        Operario worker = operarioServicio.obtenerOperarioPorId(id);

        if(worker != null){

            Map<String, Long> prestamosPorTool = worker.getOperaciones().stream()
                    .filter(operacion -> (operacion.getTipo()==1) && (operacion.getKit().isEmpty()))
                    .flatMap(operacion -> operacion.getHerramienta().stream())
                    .map(itemHerramienta -> itemHerramienta.getHerramienta().getNombre())
                    .collect(Collectors.groupingBy(nombre -> nombre, Collectors.counting()));

            Set<String> toolLabels = metodos.getAllKeys(prestamosPorTool,prestamosPorTool);

            List<String> labels = new ArrayList<>(toolLabels);
            Collections.sort(labels);

            List<Long> valuesFreqTools = metodos.getOrderedValues(prestamosPorTool,labels);

            List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(labels.size());

            List<String> coloresBorde = listaColores.stream()
                    .map(ColorGeneratorByPairs.ColorPair::getBorderColorRGBA).toList();

            List<String> coloresFondo = listaColores.stream()
                    .map(ColorGeneratorByPairs.ColorPair::getBackgroundColorRGBA).toList();

            Map<String,Object> chartData = new HashMap<>();
            chartData.put("labels",labels);
            chartData.put("datasets", Arrays.asList(createDataset("Frecuencia de uso",valuesFreqTools,coloresBorde,coloresFondo)));

            Gson gson = new Gson();

            return gson.toJson(chartData);
        }
        return null;
    }

    public String getJsonWorkersKits(String id){
        Operario worker = operarioServicio.obtenerOperarioPorId(id);

        if(worker != null){

            Map<String, Long> prestamosPorKit = worker.getOperaciones().stream()
                    .filter(operacion -> (operacion.getTipo()==1) && (!operacion.getKit().isEmpty()))
                    .flatMap(operacion -> operacion.getKit().stream())
                    .map(Kit::getNombre)
                    .collect(Collectors.groupingBy(nombre -> nombre, Collectors.counting()));

            Set<String> kitLabels = metodos.getAllKeys(prestamosPorKit,prestamosPorKit);

            List<String> labels = new ArrayList<>(kitLabels);
            Collections.sort(labels);

            List<Long> valuesFreqKits = metodos.getOrderedValues(prestamosPorKit,labels);

            List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(labels.size());

            List<String> coloresBorde = listaColores.stream()
                    .map(ColorGeneratorByPairs.ColorPair::getBorderColorRGBA).toList();

            List<String> coloresFondo = listaColores.stream()
                    .map(ColorGeneratorByPairs.ColorPair::getBackgroundColorRGBA).toList();

            Map<String,Object> chartData = new HashMap<>();
            chartData.put("labels",labels);
            chartData.put("datasets", Arrays.asList(createDataset("Frecuencia de uso",valuesFreqKits,coloresBorde,coloresFondo)));

            Gson gson = new Gson();

            return gson.toJson(chartData);
        }
        return null;
    }


    public String getJsonWorkersPrestamosTotales(){

        List<Operario> workers = operarioServicio.listarOperarios();

        Map<String,Long> prestamosByWorker = workers.stream()
                .collect(Collectors.toMap(
                        Operario::getNombre,
                        operario -> operario.getOperaciones().stream()
                                .filter(operacion -> (operacion.getTipo()==1)).count()
                ));

        Map<String,Long> devolucionesByWorker = workers.stream()
                .collect(Collectors.toMap(
                        Operario::getNombre,
                        operario -> operario.getOperaciones().stream()
                                .filter(operacion -> (operacion.getTipo()==2)).count()
                ));

        Set<String> wokersLabels = metodos.getAllKeys(prestamosByWorker,devolucionesByWorker);

        List<String> labels = new ArrayList<>(wokersLabels);
        Collections.sort(labels);

        List<Long> valuesWorkerPrestamos = metodos.getOrderedValues(prestamosByWorker,labels);
        List<Long> valuesWorkerDevoluciones = metodos.getOrderedValues(devolucionesByWorker,labels);

        List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(labels.size());

        List<String> coloresBorde = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBorderColorRGBA).toList();

        List<String> coloresFondo = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBackgroundColorRGBA).toList();

        Map<String,Object> chartData = new HashMap<>();
        chartData.put("labels",labels);
        chartData.put("datasets", Arrays.asList(
                createDataset("Prestamos",valuesWorkerPrestamos,coloresBorde,coloresBorde),
                createDataset("Devoluciones",valuesWorkerDevoluciones,coloresFondo,coloresFondo)));

        Gson gson = new Gson();

        return gson.toJson(chartData);

    }

    public String getJsonWorkersByRole(){

        List<Operario> workers = operarioServicio.listarOperarios();

        Map<String,Long> workersByRole = workers.stream()
                .collect(Collectors.groupingBy(Operario::getRol,Collectors.counting()));

        Set<String> roleLabels = metodos.getAllKeys(workersByRole,workersByRole);

        List<String> labels = new ArrayList<>(roleLabels);
        Collections.sort(labels);

        List<Long> valuesWorkersByRole = metodos.getOrderedValues(workersByRole,labels);

        List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(labels.size());

        List<String> coloresBorde = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBorderColorRGBA).toList();

        List<String> coloresFondo = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBackgroundColorRGBA).toList();

        Map<String,Object> chartData = new HashMap<>();
        chartData.put("labels",labels);
        chartData.put("datasets", Arrays.asList(
                createDataset("Operarios",valuesWorkersByRole,coloresBorde,coloresFondo)));

        Gson gson = new Gson();

        return gson.toJson(chartData);

    }


    private Map<String, Object> createDataset(String label, List<Long> data, List<String> borderColor, List<String> backgroundColor) {
        Map<String, Object> dataset = new HashMap<>();
        dataset.put("label", label);
        dataset.put("data", data);
        dataset.put("borderColor", borderColor);
        dataset.put("backgroundColor", backgroundColor);
        dataset.put("fill", true);
        return dataset;
    }
}
