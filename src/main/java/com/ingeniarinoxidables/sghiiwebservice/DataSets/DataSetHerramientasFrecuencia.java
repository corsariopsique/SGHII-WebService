package com.ingeniarinoxidables.sghiiwebservice.DataSets;

import com.google.gson.Gson;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.HerramientaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DataSetHerramientasFrecuencia {

    @Autowired
    private HerramientaRepositorio herramientaRepositorio;

    private final MetodosDataSets metodos = new MetodosDataSets();
    private final ColorGenerator colores = new ColorGenerator();

    public String getJson() {

        List<Herramienta> allTools = herramientaRepositorio.findAll();

        Map<String,Long> freqTools;

       freqTools = allTools.stream()
               .collect(Collectors.toMap(
                       Herramienta::getNombre,
                       herramienta -> herramienta.getItems().stream()
                               .flatMap(itemHerramienta -> itemHerramienta.getOperaciones().stream())
                               .filter(operacion -> (operacion.getTipo()==1))
                               .count()
               ));

        Set<String> toolsLabels = metodos.getAllKeys(freqTools,freqTools);

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
