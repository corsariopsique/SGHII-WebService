package com.ingeniarinoxidables.sghiiwebservice.DataSets;

import com.google.gson.Gson;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.servicio.KitServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DataSetRadarKit {

    private final ColorGeneratorByPairs coloresPares = new ColorGeneratorByPairs();

    private final MetodosDataSets metodos = new MetodosDataSets();

    @Autowired
    private TiempoDeUsoDataSet tiempoDeUsoDataSet;

    @Autowired
    private KitServicio kitServicio;

    public String getJson(String id) {
        Kit kit = kitServicio.obtenerKitPorId(id);

        if(kit != null) {

            long cantOperarios = kit.getOperaciones().stream()
                    .map(Operacion::getOperario)
                    .distinct()
                    .count();

            long cantPrestamos = kit.getOperaciones().stream()
                    .filter(operacion -> (operacion.getTipo()==1)).count();

            long tiempoDeUso = tiempoDeUsoDataSet.tiempoKitP(id);

            Map<String,Float> dataKit = new HashMap<>();

            dataKit.put("Operarios", (float) (cantOperarios*0.2));
            dataKit.put("Prestamos", (float) (cantPrestamos*0.3));
            dataKit.put("Tiempo de uso", (float) (tiempoDeUso*0.5));

            float IDA = (float) dataKit.values().stream()
                    .mapToDouble(Float::doubleValue)
                    .sum();

            Set<String> labelsData = metodos.getAllKeysFloat(dataKit,dataKit);

            List<String> labels = new ArrayList<>(labelsData);
            Collections.sort(labels);

            List<Float> valuesRadarKit = metodos.getOrderedValuesFloat(dataKit,labels);
            List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(1);

            String colorBorde = listaColores.get(0).getBorderColorRGBA();

            String colorFondo = listaColores.get(0).getBackgroundColorRGBA();

            Map<String,Object> chartData = new HashMap<>();

            chartData.put("labels", labels);
            chartData.put("IDA",IDA);
            chartData.put("datasets", Arrays.asList(
                    createDataset("Indicador de desgaste",valuesRadarKit, colorBorde,colorFondo)
            ));

            Gson gson = new Gson();

            return gson.toJson(chartData);
        }

        return null;
    }

    public String getJsonPrestamosKits() {

        List<Kit> kits = kitServicio.listarKits();

        Map<String,Long> prestamosByKit = kits.stream()
                .collect(Collectors.toMap(
                        Kit::getNombre,
                        kit -> kit.getOperaciones().stream()
                                .filter(operacion -> (operacion.getTipo()==1)).count()
                ));

        Set<String> kitsLabels = metodos.getAllKeys(prestamosByKit,prestamosByKit);

        List<String> labels = new ArrayList<>(kitsLabels);
        Collections.sort(labels);

        List<Long> valuesPrestamosKit = metodos.getOrderedValues(prestamosByKit,labels);

        List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(labels.size());

        List<String> coloresBorde = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBorderColorRGBA).toList();

        List<String> coloresFondo = listaColores.stream()
                .map(ColorGeneratorByPairs.ColorPair::getBackgroundColorRGBA).toList();

        Map<String,Object> chartData = new HashMap<>();

        chartData.put("labels", labels);
        chartData.put("datasets", Arrays.asList(
                createDatasetLongList("Prestamos por kit",valuesPrestamosKit, coloresBorde,coloresFondo)
        ));

        Gson gson = new Gson();

        return gson.toJson(chartData);
    }

    private Map<String, Object> createDataset(String label, List<Float> data, String borderColor, String backgroundColor) {
        Map<String, Object> dataset = new HashMap<>();
        dataset.put("label", label);
        dataset.put("data", data);
        dataset.put("borderColor", borderColor);
        dataset.put("backgroundColor", backgroundColor);
        dataset.put("fill", true);
        return dataset;
    }

    private Map<String, Object> createDatasetLongList(String label, List<Long> data, List<String> borderColor, List<String> backgroundColor) {
        Map<String, Object> dataset = new HashMap<>();
        dataset.put("label", label);
        dataset.put("data", data);
        dataset.put("borderColor", borderColor);
        dataset.put("backgroundColor", backgroundColor);
        dataset.put("fill", true);
        return dataset;
    }
}
