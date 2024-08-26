package com.ingeniarinoxidables.sghiiwebservice.servicio.DataSets;

import com.google.gson.Gson;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.ItemHerramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.HerramientaRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

// metodo a revisar por implementacion itemHerramienta

@Service
public class DataSetHerramientasFrecuencia {

    @Autowired
    private HerramientaRepositorio herramientaRepositorio;

    @Autowired
    private OperacionRepositorio operacionRepositorio;

    private MetodosDataSets metodos = new MetodosDataSets();
    private ColorGenerator colores = new ColorGenerator();

    public String getJson() {

        List<Operacion> opersHerramientas = operacionRepositorio.findAll();

        List<Herramienta> allTools = herramientaRepositorio.findAll();

        Map<String,Long> freqTools = new HashMap<>();

        Map<String,Long> uniqueTools = allTools.stream()
                .collect(Collectors.groupingBy(
                        Herramienta::getNombre,
                        Collectors.summingLong(Herramienta::getCantidad)
                ));


        Map <String,Object> freqToolsAux = opersHerramientas.stream()
                .filter(operacion -> (operacion.getTipo()==1))
                .collect(Collectors.toMap(
                        Operacion::getId,
                        operacion -> operacion.getHerramienta().stream()
                                .collect(Collectors.groupingBy(
                                        ItemHerramienta::getHerramienta,
                                        Collectors.counting()
                                ))

                ));

        for (Map.Entry<String, Object> entry : freqToolsAux.entrySet()) {

            Object herramientasMap = entry.getValue();

            if (herramientasMap instanceof Map) {
                Map<Herramienta, Long> herramientasFreq = (Map<Herramienta, Long>) herramientasMap;

                for (Map.Entry<Herramienta, Long> herramientaEntry : herramientasFreq.entrySet()) {
                    Herramienta herramienta = herramientaEntry.getKey();
                    Long frecuencia = herramientaEntry.getValue();
                    if(freqTools.containsKey(herramienta.getNombre())){
                        Long auxValue = freqTools.get(herramienta.getNombre());
                        freqTools.replace(herramienta.getNombre(),auxValue+frecuencia);
                    }else{
                        freqTools.put(herramienta.getNombre(),frecuencia);
                    }
                }
            }
        }

        Set<String> toolsLabels = metodos.getAllKeys(freqTools,uniqueTools);

        metodos.completeDataset(freqTools,toolsLabels);

        List<String> labels = new ArrayList<>(toolsLabels);
        Collections.sort(labels);

        List<Long> valueFreqTools = metodos.getOrderedValues(freqTools,labels);
        List<String> toolColors = colores.generateRGBAColors(labels.size());

        Map<String,Object> chartData = new HashMap<>();
        chartData.put("labels",labels);
        chartData.put("datasets", Arrays.asList(createDataset("Frecuencia de uso",valueFreqTools,toolColors,toolColors)));

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
