package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.HerramientaResumenDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.HerramientaResumenPorIdDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListadoKitsTopDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListadoOperariosTopDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorHerramientas;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorListadoKitsTopDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorListadoOperariosTopDto;
import com.ingeniarinoxidables.sghiiwebservice.modelo.*;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HerramientaServicio {

    @Autowired
    private HerramientaRepositorio repositorio;

    @Autowired
    private OperacionRepositorio operacionRepositorio;

    @Autowired
    private OperarioRepositorio operarioRepositorio;

    @Autowired
    private KitRepositorio kitRepositorio;

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

    // metodo a revisar por implementacion itemHerramienta
    public Herramienta eliminarHerramienta(String id) {
        Optional<Herramienta> toolDeBaja = repositorio.findById(id);
        if (toolDeBaja.isPresent()){
            toolDeBaja.get().setEstado(true);
            toolDeBaja.get().setFecha_out(LocalDate.now());
            return repositorio.save(toolDeBaja.get());
        }
        return null;
    }

    // metodo a revisar por implementacion itemHerramienta
    public HerramientaResumenDto resumen (){
        HerramientaResumenDto resumen = new HerramientaResumenDto();

        List<Herramienta> ingresosL30d = repositorio.listaHeramientaInRangoDate(LocalDate.now()
                .minusMonths(1),LocalDate.now());
        ingresosL30d.sort(new ComparadorHerramientas().reversed());

        List<Herramienta> herramientasEscasas = repositorio.listarHerramientaEscasa();

        resumen.setHerramientasReg(repositorio.herramientasTodas());
        resumen.setTotalPiezas(repositorio.piezasTotales());
        resumen.setHerramientasActivas(repositorio.herramientasActivas());
        resumen.setPiezasActivas(repositorio.piezasTotalesActivas());
        resumen.setHerramientasDeBaja(repositorio.herramientasTodas()
                - repositorio.herramientasActivas());
        resumen.setIngresosL30d(ingresosL30d);
        resumen.setHerramientaEscasa(herramientasEscasas);
        resumen.setPiezasKits(repositorio.piezasKits());
        resumen.setPiezasDisponibles(repositorio.piezasDisponibles());
        resumen.setPiezasPrestamo(repositorio.piezasTotalesActivas()
                - repositorio.piezasDisponibles() - repositorio.piezasKits());

        return resumen;
    }

    public HerramientaResumenPorIdDto resumenPorId(String id){

        HerramientaResumenPorIdDto resumen = new HerramientaResumenPorIdDto();

        Optional<Herramienta> tool = repositorio.findById(id);
        List<ListadoOperariosTopDto> listaOperariosTool = new ArrayList<>();
        List<ListadoKitsTopDto> listaKitsTool = new ArrayList<>();

        if(tool.isPresent()){

            List<Operacion> toolOperaciones = listarOperacionesTool(id);

            List<Kit> allKits = kitRepositorio.findAll();

            Map<Kit,Long> toolKits = allKits.stream()
                    .filter(kit -> kit.getHerramientas().stream()
                            .anyMatch(itemHerramienta -> tool.get().getItems().contains(itemHerramienta)))
                    .collect(Collectors.toMap(
                            kit -> kit,
                            kit -> kit.getHerramientas().stream()
                                    .filter(itemHerramienta -> tool.get().getItems().contains(itemHerramienta))
                                    .count(),
                            Long::sum
                    ));

            Map<Operario,Long> toolWorkers = toolOperaciones.stream()
                    .filter(operacion -> (operacion.getTipo()==1))
                    .collect(Collectors.toMap(
                            Operacion::getOperario,
                            operacion -> operacion.getHerramienta().stream()
                                    .filter(itemHerramienta -> tool.get().getItems().contains(itemHerramienta))
                                    .count(),
                            Long::sum
                    ));

            List<Operacion> operL30d = toolOperaciones.stream()
                    .filter(operacion -> !operacion.getFecha_operacion().isBefore(LocalDate.now().minusMonths(1))
                            && !operacion.getFecha_operacion().isAfter(LocalDate.now()))
                    .toList();


            List<Operacion> prestamos = toolOperaciones.stream()
                    .filter(operacion -> (operacion.getTipo()==1))
                    .toList();

            List<Operacion> devoluciones = toolOperaciones.stream()
                    .filter(operacion -> (operacion.getTipo()==2))
                    .toList();

            Long piezasPrestamos = prestamos.stream()
                   .flatMap(operacion -> operacion.getHerramienta().stream())
                   .filter(itemHerramienta -> tool.get().getItems().contains(itemHerramienta))
                   .count();

            Long piezasDevoluciones = devoluciones.stream()
                   .flatMap(operacion -> operacion.getHerramienta().stream())
                   .filter(itemHerramienta -> tool.get().getItems().contains(itemHerramienta))
                   .count();

            toolKits.forEach((kit,cantidad) -> {
                ListadoKitsTopDto kitTop = new ListadoKitsTopDto();
                kitTop.setKit(kit);
                kitTop.setCantidad(cantidad);
                listaKitsTool.add(kitTop);
            });

            toolWorkers.forEach((worker,cantidad) -> {
                ListadoOperariosTopDto workerTop = new ListadoOperariosTopDto();
                workerTop.setOperario(worker);
                workerTop.setCantidad(cantidad);
                listaOperariosTool.add(workerTop);
            });

            listaKitsTool.sort(new ComparadorListadoKitsTopDto().reversed());
            listaOperariosTool.sort(new ComparadorListadoOperariosTopDto().reversed());

            resumen.setTotalOper((long) toolOperaciones.size());
            resumen.setOperPrestamos((long) prestamos.size());
            resumen.setOperDevoluciones((long) devoluciones.size());
            resumen.setPiezasPrestadas(piezasPrestamos);
            resumen.setPiezasDevueltas(piezasDevoluciones);
            resumen.setOperL30d((long) operL30d.size());
            resumen.setTotalOperarios((long) toolWorkers.size());
            resumen.setTotalKits((long) toolKits.size());
            resumen.setListaUsoOperarios(listaOperariosTool);
            resumen.setListaUsoKits(listaKitsTool);
            resumen.setItemsTool(tool.get().getItems());

            return resumen;
        }
        return null;
    }


    public List<Operacion> listarOperacionesTool (String id){
        Optional<Herramienta> toolOper = repositorio.findById(id);
        if (toolOper.isPresent()){
            List<Operacion> operacions = operacionRepositorio.findAll();
            List<Operacion> opersTool = operacions.stream()
                    .filter(operacion -> (operacion.getHerramienta().stream().anyMatch(itemHerramienta ->
                            toolOper.get().getItems().contains(itemHerramienta)))).toList();

            return opersTool;
        }
        return null;
    }

    public List<Proveedor> listarProveedores (String id){
        Optional<Herramienta> toolSuplier = repositorio.findById(id);
        if (toolSuplier.isPresent()){
            List<Proveedor> proveedores = toolSuplier.get().getProveedor();
            return proveedores;
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
