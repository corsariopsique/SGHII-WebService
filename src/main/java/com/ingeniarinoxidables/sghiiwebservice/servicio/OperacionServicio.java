package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperacionRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OperacionServicio {

    @Autowired
    private OperacionRepositorio repositorio;
    @Autowired
    private OperarioRepositorio operarioRepositorio;

    public List<Operacion> listarOperaciones() { return repositorio.findAll(); }

    public Operacion obtenerOperacionPorId(String id) { return repositorio.findById(id).orElse(null); }

    @Transactional
    public Operacion guardarOperacion(String idOperario, String id_operacion, int tipo, LocalDate fechaOperacion ) {
        Operario operario = operarioRepositorio.findById(idOperario).orElseThrow(() -> new RuntimeException("Operario no encontrado"));
        Operacion operacion = new Operacion(id_operacion,operario,tipo,fechaOperacion);
        return repositorio.save(operacion);
    }

}
