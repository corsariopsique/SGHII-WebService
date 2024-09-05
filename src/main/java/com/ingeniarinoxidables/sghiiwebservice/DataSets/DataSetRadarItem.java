package com.ingeniarinoxidables.sghiiwebservice.DataSets;

import com.google.gson.Gson;
import com.ingeniarinoxidables.sghiiwebservice.modelo.ItemHerramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.servicio.ItemHerramientaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataSetRadarItem {

    private final ColorGeneratorByPairs coloresPares = new ColorGeneratorByPairs();

    private final MetodosDataSets metodos = new MetodosDataSets();

    @Autowired
    private TiempoDeUsoDataSet tiempoDeUsoDataSet;

    @Autowired
    ItemHerramientaServicio itemHerramientaServicio;

    public String getJson(int id){
        Optional<ItemHerramienta> item = itemHerramientaServicio.obtenerItemHerramienta(id);

        if(item.isPresent()){

            long cantPrestamos = item.get().getOperaciones().stream()
                    .filter(operacion -> (operacion.getTipo()==1)).count();

            long cantOperarios = item.get().getOperaciones().stream()
                    .map(Operacion::getOperario)
                    .distinct()
                    .count();

            long tiempoDeUso = tiempoDeUsoDataSet.tiempoItemP(id);

            Map<String,Float> dataItem = new HashMap<>();

            dataItem.put("Operarios", (float) (cantOperarios*0.2));
            dataItem.put("Prestamos", (float) (cantPrestamos*0.3));
            dataItem.put("Tiempo de uso", (float) (tiempoDeUso*0.5));

            float IDA = (float) dataItem.values().stream()
                    .mapToDouble(Float::doubleValue)
                    .sum();

            Set<String> labelsData = metodos.getAllKeysFloat(dataItem,dataItem);

            List<String> labels = new ArrayList<>(labelsData);

            Collections.sort(labels);

            List<Float> valuesRadarItem = metodos.getOrderedValuesFloat(dataItem,labels);

            List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(1);

            String colorBorde = listaColores.get(0).getBorderColorRGBA();

            String colorFondo = listaColores.get(0).getBackgroundColorRGBA();

            Map<String,Object> chartData = new HashMap<>();

            chartData.put("labels", labels);
            chartData.put("IDA",IDA);
            chartData.put("datasets", Arrays.asList(
                    createDataset("Indicador de desgaste",valuesRadarItem, colorBorde,colorFondo)
            ));

            Gson gson = new Gson();

            return gson.toJson(chartData);
        }

        return null;
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
}
