package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Proveedor;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.HerramientaRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperarioRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.ProveedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    public List<Herramienta> listarHerramientasPorEstado(Boolean estado) {
        return repositorio.findByEstado(estado);
    }

    public Optional<Herramienta> obtenerHerramientaPorId(String id) {
        return repositorio.findById(id);
    }


    public Herramienta guardarHerramienta(Herramienta herramienta) {
        return repositorio.save(herramienta);
    }

    public Herramienta eliminarHerramienta(String id) {
        Optional<Herramienta> toolDeBaja = repositorio.findById(id);
        if (toolDeBaja.isPresent()){
            toolDeBaja.get().setEstado(true);
            toolDeBaja.get().setFecha_out(LocalDate.now());
            return repositorio.save(toolDeBaja.get());
        }
        return null;
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

    @Transactional
    public Herramienta dropSuplier(String idHerramienta,Proveedor Suplier){
        Optional<Herramienta> tool = repositorio.findById(idHerramienta);
        if(tool.isPresent()){
            Herramienta toolModSuplier = tool.get();
            List<Proveedor> toolSupliers = toolModSuplier.getProveedor();
            toolSupliers.remove(Suplier);
            toolModSuplier.setProveedor(toolSupliers);
            return repositorio.save(toolModSuplier);
        }
        return tool.get();
    }
}
