package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.KitRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KitServicio {

    @Autowired
    private KitRepositorio repositorio;

    public List<Kit> listarKits() {return repositorio.findAll();}

    public Kit obtenerKitPorId(String id) {return repositorio.findById(id).orElse(null);}

    public Kit guardarKit (Kit kit) {return repositorio.save(kit);}

    public void eliminarKit (String id) {repositorio.deleteById(id);}

    public Kit addHerramienta(String idKit, Herramienta herramienta){
        Optional<Kit> kitOpcional = repositorio.findById(idKit);
        if(kitOpcional.isPresent()){
            Kit kit = kitOpcional.get();
            List<Herramienta> herramientasActualizadas = kit.getHerramientas();
            herramientasActualizadas.add(herramienta);
            kit.setHerramientas(herramientasActualizadas);
            return repositorio.save(kit);
        }
        return null;
    }

    public Kit deleteHerramientas (String idkit){
        Optional<Kit> kitVacio = repositorio.findById(idkit);
        if(kitVacio.isPresent()){
            Kit kitTemporal = kitVacio.get();
            List<Herramienta> listaVacia = new ArrayList<>();
            listaVacia.clear();
            kitTemporal.setHerramientas(listaVacia);
            return repositorio.save(kitTemporal);
        }
        return null;
    }

}
