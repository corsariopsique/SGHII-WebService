package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperacionRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class OperacionServicio {

    @Autowired
    private OperacionRepositorio repositorio;
    @Autowired
    private OperarioRepositorio operarioRepositorio;

    public List<Operacion> listarOperaciones() { return repositorio.findAll(); }

    public Operacion obtenerOperacionPorId(String id) { return repositorio.findById(id).orElse(null); }

    @Transactional
    public Operacion guardarOperacion(String idOperador, Operacion operacion ) {
        Operario operario = operarioRepositorio.findById(idOperador).orElseThrow(() -> new RuntimeException("Operario no encontrado"));
        Operacion nueva_Operacion = operacion;
        nueva_Operacion.setOperario(operario);
        return repositorio.save(operacion);
    }
    @Transactional
    public Operacion addTool(String idOperacion, Herramienta herramienta){
        Optional<Operacion> operacionOpcional = repositorio.findById(idOperacion);
        if(operacionOpcional.isPresent()){
            Operacion operacion = operacionOpcional.get();
            operacion.setHerramienta(herramienta);
            return repositorio.save(operacion);
        }
        return null;
    }
    @Transactional
    public Operacion addKit(String idOperacion, Kit kit){
        Optional<Operacion> operacionOpcional = repositorio.findById(idOperacion);
        if(operacionOpcional.isPresent()){
            Operacion operacion = operacionOpcional.get();
            operacion.setKit(kit);
            return repositorio.save(operacion);
        }
        return null;
    }

}
