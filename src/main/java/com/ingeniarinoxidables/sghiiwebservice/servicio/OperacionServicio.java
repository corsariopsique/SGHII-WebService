package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OperacionServicio {

    @Autowired
    private OperacionRepositorio repositorio;

    public List<Operacion> listarOperaciones() { return repositorio.findAll(); }

    public Operacion obtenerOperacionPorId(String id) { return repositorio.findById(id).orElse(null); }

    public Operacion guardarOperacion(Operacion operacion) { return repositorio.save(operacion); }

}
