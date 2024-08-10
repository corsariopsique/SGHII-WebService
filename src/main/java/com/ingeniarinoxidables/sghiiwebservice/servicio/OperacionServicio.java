package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListadoHerramientasTopDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListadoKitsTopDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.OperacionesResumenDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorOperaciones;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.HerramientaRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.KitRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperacionRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OperacionServicio {

    @Autowired
    private OperacionRepositorio repositorio;
    @Autowired
    private OperarioRepositorio operarioRepositorio;
    @Autowired
    private HerramientaRepositorio herramientaRepositorio;
    @Autowired
    private KitRepositorio kitRepositorio;

    public List<Operacion> listarOperaciones() {
        List<Operacion> operaciones =  repositorio.findAll();
        operaciones.sort(new ComparadorOperaciones().reversed());
        return operaciones;
    }

    public Operacion obtenerOperacionPorId(String id) { return repositorio.findById(id).orElse(null); }

    @Transactional
    public Operacion guardarOperacion(String idOperador, Operacion operacion ) {
        Operario operario = operarioRepositorio.findById(idOperador).orElseThrow(() -> new RuntimeException("Operario no encontrado"));
        Operacion nueva_Operacion = operacion;
        nueva_Operacion.setOperario(operario);
        return repositorio.save(operacion);
    }

    public OperacionesResumenDto resumen(){
        OperacionesResumenDto resumen = new OperacionesResumenDto();

        List<ListadoHerramientasTopDto> listaToolsDL7d = new ArrayList<>();
        List<ListadoHerramientasTopDto> listaToolsPL7d = new ArrayList<>();
        List<ListadoKitsTopDto> listaKitsPL7d = new ArrayList<>();
        List<ListadoKitsTopDto> listaKitsDL7d = new ArrayList<>();
        List<Operacion> todas  = repositorio.findAll();

        double contOper = 0;
        List<Object[]> workerOper = repositorio.promedioOperOperador();

        for (Object[] obj: workerOper){
            Long oper = (Long) obj[0];
            contOper = contOper + oper;
        }

       double avgOperWorker = contOper/ workerOper.size();

        Double promedioOperTrabajador = avgOperWorker;

        Long operL30dTools = repositorio.conteoOperFechaTools(LocalDate.now().minusMonths(1), LocalDate.now());
        Long operL30dKits = repositorio.conteoOperFechaKits(LocalDate.now().minusMonths(1), LocalDate.now());

        List<Operacion> operL7dPrestamos = todas.stream()
                .filter(operacion -> (operacion.getTipo()==1))
                .filter(operacion -> !operacion.getFecha_operacion().isBefore(LocalDate.now().minusDays(7))
                        && !operacion.getFecha_operacion().isAfter(LocalDate.now())).toList();

        List<Operacion> operL7dDevoluciones = todas.stream()
                .filter(operacion -> (operacion.getTipo()==2))
                .filter(operacion -> !operacion.getFecha_operacion().isBefore(LocalDate.now().minusDays(7))
                        && !operacion.getFecha_operacion().isAfter(LocalDate.now())).toList();

        Map <String,Long> workerOperL7dPrestamos = operL7dPrestamos.stream()
                        .collect(Collectors.groupingBy(operacion -> operacion.getOperario().getId(),Collectors.counting()));

        Map <String,Long> workerOperL7dDevoluciones = operL7dDevoluciones.stream()
                .collect(Collectors.groupingBy(operacion -> operacion.getOperario().getId(),Collectors.counting()));


        Map <String,Map <String, Long>> toolsOperL7dDevoluciones = operL7dDevoluciones.stream()
                        .collect(Collectors.toMap(
                                Operacion::getId,
                                operacion -> operacion.getHerramienta().stream()
                                        .collect(Collectors.groupingBy(
                                                Herramienta::getId,
                                                Collectors.counting()
                                        )),
                                (existing, replacement) -> {
                                    replacement.forEach(
                                            (key, value) -> existing.merge(key, value, Long::max)
                                    );
                                    return existing;
                                }

                        ));


        Map <String,Map <String, Long>> toolsOperL7dPrestamos = operL7dPrestamos.stream()
                .collect(Collectors.toMap(
                        Operacion::getId,
                        operacion -> operacion.getHerramienta().stream()
                                .collect(Collectors.groupingBy(
                                        Herramienta::getId,
                                        Collectors.counting()
                                )),
                        (existing, replacement) -> {
                            replacement.forEach(
                                    (key, value) -> existing.merge(key, value, Long::max)
                            );
                            return existing;
                        }

                ));

        Map <String,Map <String, Long>> kitsOperL7dDevoluciones = operL7dDevoluciones.stream()
                .collect(Collectors.toMap(
                        Operacion::getId,
                        operacion -> operacion.getKit().stream()
                                .collect(Collectors.groupingBy(
                                        Kit::getId,
                                        Collectors.counting()
                                ))
                ));

        Map <String,Map <String, Long>> kitsOperL7dPrestamos = operL7dPrestamos.stream()
                .collect(Collectors.toMap(
                        Operacion::getId,
                        operacion -> operacion.getKit().stream()
                                .collect(Collectors.groupingBy(
                                        Kit::getId,
                                        Collectors.counting()
                                ))
                ));

        toolsOperL7dPrestamos.forEach((idOper,listaHerramientas) -> {
            listaHerramientas.forEach((idTool,cantidad) -> {
                ListadoHerramientasTopDto item = new ListadoHerramientasTopDto();
                item.setTool(herramientaRepositorio.findById(idTool).get());
                item.setCantidad(cantidad);
                listaToolsPL7d.add(item);
            });
        });

        toolsOperL7dDevoluciones.forEach((idOper,listaHerramientas) -> {
            listaHerramientas.forEach((idTool,cantidad) -> {
                ListadoHerramientasTopDto item = new ListadoHerramientasTopDto();
                item.setTool(herramientaRepositorio.findById(idTool).get());
                item.setCantidad(cantidad);
                listaToolsDL7d.add(item);
            });
        });

        kitsOperL7dPrestamos.forEach((idOper,listaKits) -> {
            listaKits.forEach((idKit,cantidad) -> {
                ListadoKitsTopDto item = new ListadoKitsTopDto();
                item.setKit(kitRepositorio.findById(idKit).get());
                item.setCantidad(cantidad);
                listaKitsPL7d.add(item);
            });
        });

        kitsOperL7dDevoluciones.forEach((idOper,listaKits) -> {
            listaKits.forEach((idKit,cantidad) -> {
                ListadoKitsTopDto item = new ListadoKitsTopDto();
                item.setKit(kitRepositorio.findById(idKit).get());
                item.setCantidad(cantidad);
                listaKitsDL7d.add(item);
            });
        });

        resumen.setKitsOperL7dD((long) listaKitsDL7d.size());
        resumen.setKitsOperL7dP((long) listaKitsPL7d.size());
        resumen.setToolsOperL7dD((long) listaToolsDL7d.size());
        resumen.setToolsOperL7dP((long) listaToolsPL7d.size());
        resumen.setOperL7dD((long)operL7dDevoluciones.size());
        resumen.setOperL7dP((long)operL7dPrestamos.size());
        resumen.setWorkersOperDL7d((long) workerOperL7dDevoluciones.size());
        resumen.setWorkersOperPL7d((long) workerOperL7dPrestamos.size());
        resumen.setTotalOperaciones(todas.size());
        resumen.setPrestamos(repositorio.contadorOperacionesTipo(1));
        resumen.setDevoluciones(repositorio.contadorOperacionesTipo(2));
        resumen.setPromedioOperWorker(promedioOperTrabajador);
        resumen.setOperL30dTools(operL30dTools);
        resumen.setOperL30dKits(operL30dKits);

        return resumen;
    }

}
