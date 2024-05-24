package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OperarioServicio {

    @Autowired
    private OperarioRepositorio repositorio;

    public List<Operario> listarOperarios() { return repositorio.findAll(); }

    public Operario obtenerOperarioPorId(String id) { return repositorio.findById(id).orElse(null); }

    public Operario guardarOperario(Operario operario) { return repositorio.save(operario); }

    public void eliminarOperario(String id) { repositorio.deleteById(id); }


}
