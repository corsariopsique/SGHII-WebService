package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.HerramientaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HerramientaServicio {

    @Autowired
    private HerramientaRepositorio repositorio;

    public List<Herramienta> listarHerramientas() {
        return repositorio.findAll();
    }

    public Herramienta obtenerHerramientaPorId(String id) {
        return repositorio.findById(id).orElse(null);
    }

    public Herramienta guardarHerramienta(Herramienta herramienta) {
        return repositorio.save(herramienta);
    }

    public void eliminarHerramienta(String id) {
        repositorio.deleteById(id);
    }

}
