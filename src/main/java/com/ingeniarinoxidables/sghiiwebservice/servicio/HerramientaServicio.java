package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Proveedor;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.HerramientaRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperarioRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.ProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HerramientaServicio {

    @Autowired
    private HerramientaRepositorio repositorio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private OperarioRepositorio operarioRepositorio;

    public List<Herramienta> listarHerramientas() {
        return repositorio.findAll();
    }

    public Optional<Herramienta> obtenerHerramientaPorId(String id) {
        return repositorio.findById(id);
    }

    @Transactional
    public Herramienta guardarHerramienta(Herramienta herramienta) {
        return repositorio.save(herramienta);
    }

    public void eliminarHerramienta(String id) {
        repositorio.deleteById(id);
    }

    @Transactional
    public Herramienta addProveedor(String idHerramienta, Proveedor proveedor){
        Optional<Herramienta> herramientaOpcional = repositorio.findById(idHerramienta);
        if(herramientaOpcional.isPresent()){
            Herramienta herramienta = herramientaOpcional.get();
            List<Proveedor> proveedoresActualizados = herramienta.getProveedor();
            proveedoresActualizados.add(proveedor);
            herramienta.setProveedor(proveedoresActualizados);
            return repositorio.save(herramienta);
        }
        return null;
    }

}
