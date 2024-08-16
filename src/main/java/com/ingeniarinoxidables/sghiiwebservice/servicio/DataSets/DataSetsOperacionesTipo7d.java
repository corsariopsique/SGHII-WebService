package com.ingeniarinoxidables.sghiiwebservice.servicio.DataSets;

import com.google.gson.Gson;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataSetsOperacionesTipo7d {

    @Autowired
    private OperacionRepositorio operacionRepositorio;
    private MetodosDataSets metodos = new MetodosDataSets();

    public String jsonData(){

        List<Operacion> opers = operacionRepositorio.operacionesRangoFecha(
                LocalDate.now().minusDays(7),LocalDate.now());

        Map <LocalDate,Long> prestamos = dataOperByType(opers,1);

        Map <LocalDate,Long> devoluciones = dataOperByType(opers,2);

        Map <String,Long> prestamoS = convertKeysToString(prestamos);
        Map <String,Long> devolucionS = convertKeysToString((devoluciones));

        Set<String> fechasLabels = metodos.getAllKeys(prestamoS,devolucionS);

        metodos.completeDataset(prestamoS,fechasLabels);
        metodos.completeDataset(devolucionS,fechasLabels);

        List<String> labels = new ArrayList<>(fechasLabels);
        Collections.sort(labels);

        List<Long> valoresPrestamos = metodos.getOrderedValues(prestamoS,labels);
        List<Long> valoresDevoluciones = metodos.getOrderedValues(devolucionS,labels);

        Map<String, Object> chartData = new HashMap<>();
        chartData.put("labels", labels);
        chartData.put("datasets", Arrays.asList(
                createDataset("Prestamos", valoresPrestamos, "rgba(33, 115, 231, 0.77)", "rgba(204, 219, 240, 0.55)"),
                createDataset("Devoluciones", valoresDevoluciones, "rgba(32, 236, 155, 0.8)", "rgba(200, 237, 222, 0.49)")
        ));

        Gson gson = new Gson();

        return gson.toJson(chartData);
    }

    private Map<String, Long> convertKeysToString(Map<LocalDate,Long> map) {
        Map<String, Long> result = new HashMap<>();
        for (Map.Entry<LocalDate, Long> entry : map.entrySet()) {
            String formattedKey = entry.getKey().toString();
            result.put(formattedKey, entry.getValue());
        }
        return result;
    }

    private Map<LocalDate,Long> dataOperByType (List<Operacion> oper, int tipo){

        Map<LocalDate,Long> operByDate = oper.stream()
                .filter(operacion -> (operacion.getTipo()==tipo))
                .collect(Collectors.groupingBy(
                        Operacion::getFecha_operacion,
                        Collectors.counting()
                ));
        return operByDate;
    }

    private Map<String, Object> createDataset(String label, List<Long> data, String borderColor, String backgroundColor) {
        Map<String, Object> dataset = new HashMap<>();
        dataset.put("label", label);
        dataset.put("data", data);
        dataset.put("borderColor", borderColor);
        dataset.put("backgroundColor", backgroundColor);
        dataset.put("fill", true);
        return dataset;
    }
}
