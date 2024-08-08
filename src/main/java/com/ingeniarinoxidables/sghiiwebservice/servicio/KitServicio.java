package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.KitResumenDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.KitResumenPorIdDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListadoOperariosTopDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorOperaciones;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.KitRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KitServicio {

    @Autowired
    private KitRepositorio repositorio;

    @Autowired
    private OperarioRepositorio operarioRepositorio;

    public List<Kit> listarKits() {return repositorio.findAll();}

    public List<Kit> listarKitsPorEstado(Boolean estado){
        return repositorio.findByEstado(estado);
    }

    public Kit obtenerKitPorId(String id) {return repositorio.findById(id).orElse(null);}

    public Kit guardarKit (Kit kit) {return repositorio.save(kit);}

    public Kit eliminarKit (String id) {
        Optional<Kit> kitDeBaja = repositorio.findById(id);
        if(kitDeBaja.isPresent()){
            kitDeBaja.get().setEstado(true);
            kitDeBaja.get().setFecha_out(LocalDate.now());
            List<Herramienta> listaVacia = new ArrayList<>();
            listaVacia.clear();
            kitDeBaja.get().setHerramientas(listaVacia);
            return repositorio.save(kitDeBaja.get());
        }
        return null;
    }

    public KitResumenDto resumen (){
        KitResumenDto resumen = new KitResumenDto();
        List<Kit> todos = repositorio.findAll();
        resumen.setKitsReg(todos.size());
        int contActivos = 0;
        int contPiezas = 0;
        int contKitP = 0;
        for (Kit kit : todos){
            if(!kit.getEstado()){
                ++contActivos;
                contPiezas = contPiezas + kit.getHerramientas().size();
                if (kit.getDisponible()==1){
                    ++contKitP;
                }
            }
        }
        resumen.setTotalPiezasKits(contPiezas);
        resumen.setKitsActivos(contActivos);
        resumen.setKitsDeBaja(todos.size()-contActivos);
        resumen.setKitsPrestados(contKitP);
        resumen.setKitsDisponibles(contActivos-contKitP);
        return resumen;
    }

    public KitResumenPorIdDto resumenPorId(String id){

        Optional<Kit> kit = repositorio.findById(id);
        List<ListadoOperariosTopDto> listaOperariosKits = new ArrayList<>();
        KitResumenPorIdDto resumen = new KitResumenPorIdDto();

        if (kit.isPresent()){
            List<Operacion> operKits = kit.get().getOperaciones();

            List<Operacion> prestamos = operKits.stream()
                    .filter(operacion -> operacion.getTipo() == 1).toList();

            List<Operacion> devoluciones = operKits.stream()
                    .filter(operacion -> operacion.getTipo() == 2).toList();

            List<Operacion> operL30d = operKits.stream()
                    .filter(operacion -> !operacion.getFecha_operacion().isBefore(LocalDate.now().minusMonths(1))
                            && !operacion.getFecha_operacion().isAfter(LocalDate.now())).toList();

            Map<String,Long> operariosKits = operKits.stream()
                    .filter(operacion -> operacion.getTipo() == 1)
                    .collect(Collectors.groupingBy(operacion ->
                            operacion.getOperario().getId(),Collectors.counting()));

            operariosKits.forEach((idworker,cantidad) -> {
                ListadoOperariosTopDto elementoOperarioKit = new ListadoOperariosTopDto();
                elementoOperarioKit.setOperario(operarioRepositorio.findById(idworker).get());
                elementoOperarioKit.setCantidad(cantidad);
                listaOperariosKits.add(elementoOperarioKit);
            });

            resumen.setOperPrestamo((long) prestamos.size());
            resumen.setOperDevolucion((long) devoluciones.size());
            resumen.setOperL30d((long) operL30d.size());
            resumen.setTotalOperarios((long) listaOperariosKits.size());
            resumen.setListaUsoOperarios(listaOperariosKits);

            return resumen;
        }
        return null;
    }

    public List<Operacion> listarOperacionesKits (String id){
        Optional<Kit> kitOperaciones = repositorio.findById(id);
        if(kitOperaciones.isPresent()){
            List<Operacion> operaciones = kitOperaciones.get().getOperaciones();
            operaciones.sort(new ComparadorOperaciones().reversed());
            return operaciones;
        }
        return null;
    }

    @Transactional
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

    @Transactional
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
