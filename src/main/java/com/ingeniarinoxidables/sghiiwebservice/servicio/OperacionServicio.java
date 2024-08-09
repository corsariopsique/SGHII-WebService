package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.OperacionesResumenDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorOperaciones;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperacionRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class OperacionServicio {

    @Autowired
    private OperacionRepositorio repositorio;
    @Autowired
    private OperarioRepositorio operarioRepositorio;

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
        List<Operacion> todas  = repositorio.findAll();

        double contOper = 0;
        List<Object[]> workerOper = repositorio.promedioOperOperador();

        for (Object[] obj: workerOper){
            Long oper = (Long) obj[0];
            contOper = contOper + oper;
        }

       double avgOperWorker = contOper/ workerOper.size();

        Double promedioHerramientaOperacion = repositorio.promedioToolsOper();
        Double promedioOperTrabajador = avgOperWorker;

        Long operL1d = repositorio.conteoOperacionesFecha(LocalDate.now().minusDays(1),LocalDate.now());
        Long operL7d = repositorio.conteoOperacionesFecha(LocalDate.now().minusDays(7),LocalDate.now());
        Long operL30d = repositorio.conteoOperacionesFecha(LocalDate.now().minusMonths(1), LocalDate.now());

        Long operL30dTools = repositorio.conteoOperFechaTools(LocalDate.now().minusMonths(1), LocalDate.now());
        Long operL30dKits = repositorio.conteoOperFechaKits(LocalDate.now().minusMonths(1), LocalDate.now());

        resumen.setTotalOperaciones(todas.size());
        resumen.setPrestamos(repositorio.contadorOperacionesTipo(1));
        resumen.setDevoluciones(repositorio.contadorOperacionesTipo(2));
        resumen.setToolMeanOperacion(promedioHerramientaOperacion);
        resumen.setOperTool(repositorio.findOperByTools());
        resumen.setOperKit(repositorio.findOperByKits());
        resumen.setPromedioOperWorker(promedioOperTrabajador);
        resumen.setOperDay(operL1d);
        resumen.setOperWeek(operL7d);
        resumen.setOperMonth(operL30d);
        resumen.setOperL30dTools(operL30dTools);
        resumen.setOperL30dKits(operL30dKits);

        return resumen;
    }

}
