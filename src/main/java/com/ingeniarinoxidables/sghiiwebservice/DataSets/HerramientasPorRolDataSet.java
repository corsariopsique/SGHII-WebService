package com.ingeniarinoxidables.sghiiwebservice.DataSets;

import com.google.gson.Gson;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.servicio.HerramientaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class HerramientasPorRolDataSet {

    @Autowired
    private HerramientaServicio herramientaServicio;

    private final MetodosDataSets metodos = new MetodosDataSets();

    private final ColorGeneratorByPairs coloresPares = new ColorGeneratorByPairs();

    public String getJsonRole(){

        List<Herramienta> tools = herramientaServicio.listarHerramientasPorEstado(false);

        Map<String,Long> toolsByRole = tools.stream()
                .collect(Collectors.groupingBy(Herramienta::getRol,Collectors.counting()));

        Set<String> roleLabels = metodos.getAllKeys(toolsByRole,toolsByRole);

        List<String> labels = new ArrayList<>(roleLabels);
        Collections.sort(labels);

        List<Long> valuesToolsByRole = metodos.getOrderedValues(toolsByRole,labels);

        List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(labels.size());

        List<String> coloresBorde = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBorderColorRGBA).toList();

        List<String> coloresFondo = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBackgroundColorRGBA).toList();

        Map<String,Object> chartData = new HashMap<>();
        chartData.put("labels",labels);
        chartData.put("datasets", Arrays.asList(createDataset("Herramientas por rol",valuesToolsByRole,coloresBorde,coloresFondo)));

        Gson gson = new Gson();

        return gson.toJson(chartData);
    }

    public String getJsonCat(){

        List<Herramienta> tools = herramientaServicio.listarHerramientasPorEstado(false);

        Map<String,Long> toolsByCat = tools.stream()
                .collect(Collectors.groupingBy(Herramienta::getCategoria,Collectors.counting()));

        Set<String> catLabels = metodos.getAllKeys(toolsByCat,toolsByCat);

        List<String> labels = new ArrayList<>(catLabels);
        Collections.sort(labels);

        List<Long> valuesToolsByCat = metodos.getOrderedValues(toolsByCat,labels);

        List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(labels.size());

        List<String> coloresBorde = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBorderColorRGBA).toList();

        List<String> coloresFondo = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBackgroundColorRGBA).toList();

        Map<String,Object> chartData = new HashMap<>();
        chartData.put("labels",labels);
        chartData.put("datasets", Arrays.asList(createDataset("Herramientas por categoria",valuesToolsByCat,coloresBorde,coloresFondo)));

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
