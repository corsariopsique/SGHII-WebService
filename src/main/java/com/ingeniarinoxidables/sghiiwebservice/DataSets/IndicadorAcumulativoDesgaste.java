package com.ingeniarinoxidables.sghiiwebservice.DataSets;

import com.google.gson.Gson;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.ItemHerramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.servicio.HerramientaServicio;
import com.ingeniarinoxidables.sghiiwebservice.servicio.KitServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class IndicadorAcumulativoDesgaste {

    @Autowired
    private HerramientaServicio herramientaServicio;

    @Autowired
    private KitServicio kitServicio;

    @Autowired
    private TiempoDeUsoDataSet tiempoDeUsoDataSet;

    private final MetodosDataSets metodos = new MetodosDataSets();

    private final ColorGeneratorByPairs coloresPares = new ColorGeneratorByPairs();

    public String getJsonTool(String id){
        Optional<Herramienta> tool = herramientaServicio.obtenerHerramientaPorId(id);

        if(tool.isPresent()){

            Map<Integer,Long> cantOperariosByItem = tool.get().getItems().stream()
                    .collect(Collectors.toMap(
                            ItemHerramienta::getId,
                            itemHerramienta -> itemHerramienta.getOperaciones().stream()
                                    .map(Operacion::getOperario)
                                    .distinct()
                                    .count()
                    ));

            Map<Integer,Long> cantPrestamosByItem = tool.get().getItems().stream()
                    .collect(Collectors.toMap(
                            ItemHerramienta::getId,
                            itemHerramienta -> itemHerramienta.getOperaciones().stream()
                                    .filter(operacion -> (operacion.getTipo()==1)).count()
                    ));

            Map<Integer,Long> tiempoDeUsoByItem = new HashMap<>();

            for (ItemHerramienta item: tool.get().getItems()){
                tiempoDeUsoByItem.put(item.getId(),
                        tiempoDeUsoDataSet.tiempoItemP(item.getId()));
            }

            Map<Integer,Float> indiceByItem = new HashMap<>();

            for (ItemHerramienta item: tool.get().getItems()){
                float indiceItem = (float) ((cantOperariosByItem.get(item.getId())*0.2) +
                        (cantPrestamosByItem.get(item.getId())*0.3) +
                        (tiempoDeUsoByItem.get(item.getId())*0.5));

                indiceByItem.put(item.getId(), indiceItem);
            }

            Set<Integer> itemLabels = metodos.getAllKeysFloatItems(indiceByItem,indiceByItem);

            List<Integer> labels = new ArrayList<>(itemLabels);

            Collections.sort(labels);

            List<Float> valuesIndiceItems = metodos.getOrderedValuesFloatItems(indiceByItem,labels);
            List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(labels.size());

            List<String> coloresBorde = listaColores.stream()
                    .map(ColorGeneratorByPairs.ColorPair::getBorderColorRGBA).toList();

            List<String> coloresFondo = listaColores.stream()
                    .map(ColorGeneratorByPairs.ColorPair::getBackgroundColorRGBA).toList();

            Map<String, Object> chartData = new HashMap<>();
            chartData.put("labels", labels);
            chartData.put("datasets", Arrays.asList(
                    createDataset("IDA", valuesIndiceItems, coloresBorde, coloresFondo)
            ));

            Gson gson = new Gson();

            return gson.toJson(chartData);
        }
        return null;
    }

    public String getJsonKit(){

        List<Kit> kits = kitServicio.listarKits();

        if (kits != null) {

            Map<String,Long> cantOperariosKits = kits.stream()
                    .collect(Collectors.toMap(
                            Kit::getNombre,
                            kit -> kit.getOperaciones().stream()
                                    .map(Operacion::getOperario)
                                    .distinct()
                                    .count()
                    ));

            Map<String,Long> cantPrestamosKits = kits.stream()
                    .collect(Collectors.toMap(
                            Kit::getNombre,
                            kit -> kit.getOperaciones().stream()
                                    .filter(operacion -> (operacion.getTipo()==1)).count()
                    ));

            Map<String,Long> tiempoUsoKit = kits.stream()
                    .collect(Collectors.toMap(
                            Kit::getNombre,
                            kit -> tiempoDeUsoDataSet.tiempoKitP(kit.getId())
                    ));


            Map<String,Float> indiceByKit = new HashMap<>();

            for (Kit kit: kits){
                float indiceKit = (float) ((cantOperariosKits.get(kit.getNombre())*0.2) +
                        (cantPrestamosKits.get(kit.getNombre())*0.3) +
                        (tiempoUsoKit.get(kit.getNombre())*0.5));

                indiceByKit.put(kit.getNombre(), indiceKit);
            }

            Set<String> kitsLabels = metodos.getAllKeysFloat(indiceByKit,indiceByKit);

            List<String> labels = new ArrayList<>(kitsLabels);

            Collections.sort(labels);

            List<Float> valuesIndiceKits = metodos.getOrderedValuesFloat(indiceByKit,labels);
            List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(labels.size());

            List<String> coloresBorde = listaColores.stream()
                    .map(ColorGeneratorByPairs.ColorPair::getBorderColorRGBA).toList();

            List<String> coloresFondo = listaColores.stream()
                    .map(ColorGeneratorByPairs.ColorPair::getBackgroundColorRGBA).toList();

            Map<String, Object> chartData = new HashMap<>();
            chartData.put("labels", labels);
            chartData.put("datasets", Arrays.asList(
                    createDataset("IDA", valuesIndiceKits, coloresBorde, coloresFondo)
            ));

            Gson gson = new Gson();

            return gson.toJson(chartData);
        }
        return null;
    }

    public String getJsonAllTool(){

        List<Herramienta> tools = herramientaServicio.listarHerramientas();

        if (tools != null) {

            Map<String,Long> cantOperariosTools = tools.stream()
                    .collect(Collectors.toMap(
                            Herramienta::getNombre,
                                    herramienta -> herramienta.getItems().stream()
                                            .flatMap(itemHerramienta -> itemHerramienta.getOperaciones().stream())
                                            .map(Operacion::getOperario)
                                            .distinct()
                                            .count()
                    ));

            Map<String,Long> cantPrestamosTools = tools.stream()
                    .collect(Collectors.toMap(
                            Herramienta::getNombre,
                            herramienta -> herramienta.getItems().stream()
                                    .flatMap(itemHerramienta -> itemHerramienta.getOperaciones().stream())
                                    .filter(operacion -> (operacion.getTipo()==1)).count()
                    ));

            Map<String,Long> tiempoUsoTools = tools.stream()
                    .collect(Collectors.toMap(
                            Herramienta::getNombre,
                            herramienta -> tiempoDeUsoDataSet.tiempoHerramientaP(herramienta.getId())
                    ));


            Map<String,Float> indiceByTool = new HashMap<>();

            for (Herramienta tool: tools){
                float indiceTool = (float) ((cantOperariosTools.get(tool.getNombre())*0.2) +
                        (cantPrestamosTools.get(tool.getNombre())*0.3) +
                        (tiempoUsoTools.get(tool.getNombre())*0.5));

                indiceByTool.put(tool.getNombre(), indiceTool);
            }

            Set<String> toolsLabels = metodos.getAllKeysFloat(indiceByTool,indiceByTool);

            List<String> labels = new ArrayList<>(toolsLabels);

            Collections.sort(labels);

            List<Float> valuesIndiceTools = metodos.getOrderedValuesFloat(indiceByTool,labels);
            List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(labels.size());

            List<String> coloresBorde = listaColores.stream()
                    .map(ColorGeneratorByPairs.ColorPair::getBorderColorRGBA).toList();

            List<String> coloresFondo = listaColores.stream()
                    .map(ColorGeneratorByPairs.ColorPair::getBackgroundColorRGBA).toList();

            Map<String, Object> chartData = new HashMap<>();
            chartData.put("labels", labels);
            chartData.put("datasets", Arrays.asList(
                    createDataset("IDA", valuesIndiceTools, coloresBorde, coloresFondo)
            ));

            Gson gson = new Gson();

            return gson.toJson(chartData);
        }
        return null;
    }

    private Map<String, Object> createDataset(String label, List<Float> data, List<String> borderColor, List<String> backgroundColor) {
        Map<String, Object> dataset = new HashMap<>();
        dataset.put("label", label);
        dataset.put("data", data);
        dataset.put("borderColor", borderColor);
        dataset.put("backgroundColor", backgroundColor);
        dataset.put("fill", true);
        return dataset;
    }

}
