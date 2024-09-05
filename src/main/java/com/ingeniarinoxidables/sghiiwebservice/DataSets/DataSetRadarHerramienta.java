package com.ingeniarinoxidables.sghiiwebservice.DataSets;

import com.google.gson.Gson;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.servicio.HerramientaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataSetRadarHerramienta {

    private final ColorGeneratorByPairs coloresPares = new ColorGeneratorByPairs();

    private final MetodosDataSets metodos = new MetodosDataSets();

    @Autowired
    private TiempoDeUsoDataSet tiempoDeUsoDataSet;

    @Autowired
    private HerramientaServicio herramientaServicio;

    public String getJson(String id) {
        Optional<Herramienta> tool = herramientaServicio.obtenerHerramientaPorId(id);

        if(tool.isPresent()){

            long cantOperarios = tool.get().getItems().stream()
                    .flatMap(itemHerramienta -> itemHerramienta.getOperaciones().stream())
                    .filter(operacion -> (operacion.getTipo()==1))
                    .map(Operacion::getOperario)
                    .distinct()
                    .count();

            long cantPrestamos = tool.get().getItems().stream()
                    .flatMap(itemHerramienta -> itemHerramienta.getOperaciones().stream())
                    .filter(operacion -> (operacion.getTipo()==1)).count();

            long tiempoDeUso = tiempoDeUsoDataSet.tiempoHerramientaP(id);

            Map<String,Float> dataTool = new HashMap<>();

            dataTool.put("Operarios", (float) (cantOperarios*0.2));
            dataTool.put("Prestamos", (float) (cantPrestamos*0.3));
            dataTool.put("Tiempo de uso", (float) (tiempoDeUso*0.5));

            float IDA = (float) dataTool.values().stream()
                    .mapToDouble(Float::doubleValue)
                    .sum();

            Set<String> labelsData = metodos.getAllKeysFloat(dataTool,dataTool);

            List<String> labels = new ArrayList<>(labelsData);
            Collections.sort(labels);

            List<Float> valuesRadarTool = metodos.getOrderedValuesFloat(dataTool,labels);
            List<ColorGeneratorByPairs.ColorPair> listaColores = coloresPares.generateColorPairs(1);

           String colorBorde = listaColores.get(0).getBorderColorRGBA();

           String colorFondo = listaColores.get(0).getBackgroundColorRGBA();

            Map<String,Object> chartData = new HashMap<>();

            chartData.put("labels", labels);
            chartData.put("IDA",IDA);
            chartData.put("datasets", Arrays.asList(
                    createDataset("Indicador de desgaste",valuesRadarTool, colorBorde,colorFondo)
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
