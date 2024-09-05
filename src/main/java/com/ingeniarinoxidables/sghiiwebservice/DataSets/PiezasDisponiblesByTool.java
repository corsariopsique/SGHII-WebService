package com.ingeniarinoxidables.sghiiwebservice.DataSets;

import com.google.gson.Gson;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.servicio.HerramientaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PiezasDisponiblesByTool {

    @Autowired
    private HerramientaServicio herramientaServicio;

    private final MetodosDataSets metodos = new MetodosDataSets();

    private final ColorGeneratorByPairs coloresPares = new ColorGeneratorByPairs();

    public String jsonData(){
        List<Herramienta> tools = herramientaServicio.listarHerramientasPorEstado(false);

        Map<String,Long> diponiblesByTool = tools.stream()
                .collect(Collectors.toMap(
                        Herramienta::getNombre,
                        herramienta -> herramienta.getItems().stream()
                                .filter(itemHerramienta -> itemHerramienta.getEstado()==0).count()
                ));

        Map<String,Long> noDisponiblesByTool = tools.stream()
                .collect(Collectors.toMap(
                        Herramienta::getNombre,
                        herramienta -> herramienta.getItems().stream()
                                .filter(itemHerramienta -> itemHerramienta.getEstado()==1 && itemHerramienta.getFecha_out()==null)
                                .count()
                ));

        Set<String> toolLabels = metodos.getAllKeys(diponiblesByTool,noDisponiblesByTool);

        metodos.completeDataset(diponiblesByTool,toolLabels);
        metodos.completeDataset(noDisponiblesByTool,toolLabels);

        List<String> labels = new ArrayList<>(toolLabels);
        Collections.sort(labels);

        List<Long> valuesDisponibles = metodos.getOrderedValues(diponiblesByTool,labels);
        List<Long> valuesNoDisponibles = metodos.getOrderedValues(noDisponiblesByTool,labels);

        List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(labels.size());

        List<String> coloresBorde = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBorderColorRGBA).toList();

        List<String> coloresFondo = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBackgroundColorRGBA).toList();

        Map<String,Object> chartData = new HashMap<>();
        chartData.put("labels", labels);
        chartData.put("datasets", Arrays.asList(
                createDataset("Disponibles",valuesDisponibles,coloresBorde,coloresBorde),
                createDataset("No disponibles",valuesNoDisponibles,coloresFondo,coloresFondo)
        ));

        Gson gson = new Gson();

        return gson.toJson(chartData);

    }

    public String jsonDataBajasTool(){
        List<Herramienta> tools = herramientaServicio.listarHerramientasPorEstado(false);

        Map<String,Long> bajasByTool = tools.stream()
                .collect(Collectors.toMap(
                        Herramienta::getNombre,
                        herramienta -> herramienta.getItems().stream()
                                .filter(itemHerramienta -> (itemHerramienta.getFecha_out()!=null)).count()
                ));

        Set<String> toolLabels = metodos.getAllKeys(bajasByTool,bajasByTool);

        metodos.completeDataset(bajasByTool,toolLabels);

        List<String> labels = new ArrayList<>(toolLabels);
        Collections.sort(labels);

        List<Long> valuesBajasTools = metodos.getOrderedValues(bajasByTool,labels);

        List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(labels.size());

        List<String> coloresBorde = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBorderColorRGBA).toList();

        List<String> coloresFondo = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBackgroundColorRGBA).toList();

        Map<String,Object> chartData = new HashMap<>();
        chartData.put("labels", labels);
        chartData.put("datasets", Arrays.asList(
                createDataset("Items de baja",valuesBajasTools,coloresBorde,coloresFondo)
        ));

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
