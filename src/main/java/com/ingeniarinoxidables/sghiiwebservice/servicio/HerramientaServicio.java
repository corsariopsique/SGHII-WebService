package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.HerramientaResumenDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.HerramientaResumenPorIdDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListadoKitsTopDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListadoOperariosTopDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorListadoKitsTopDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorListadoOperariosTopDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorOperaciones;
import com.ingeniarinoxidables.sghiiwebservice.modelo.*;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.HerramientaRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.KitRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperarioRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.ProveedorRepositorio;
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
    private ProveedorRepositorio proveedorRepositorio;

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

    public Herramienta eliminarHerramienta(String id) {
        Optional<Herramienta> toolDeBaja = repositorio.findById(id);
        if (toolDeBaja.isPresent()){
            toolDeBaja.get().setEstado(true);
            toolDeBaja.get().setFecha_out(LocalDate.now());
            return repositorio.save(toolDeBaja.get());
        }
        return null;
    }

    public HerramientaResumenDto resumen (){
        HerramientaResumenDto resumen = new HerramientaResumenDto();
        List<Herramienta> todas = repositorio.findAll();
        List<Herramienta> ingresosL30d = repositorio.listaHeramientaInRangoDate(LocalDate.now()
                .minusMonths(1),LocalDate.now());
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

            Map <String, Map<String,Long>> kitsTool = tool.get().getKits().stream()
                    .collect(Collectors.toMap(
                            Kit::getId,
                            kit -> kit.getHerramientas().stream()
                                    .collect(Collectors.groupingBy(
                                            Herramienta::getId,
                                            Collectors.counting()
                                    )),
                            (existing, replacement) -> {
                                replacement.forEach(
                                        (key, value) -> existing.merge(key, value, Long::max)
                                );
                                return existing;
                            }
                    ));

            Map<String, Map <String, Map<String,Long>>> workerstop = operarioRepositorio.findAll().stream()
                    .collect(Collectors.toMap(
                            Operario::getId,
                            operario -> operario.getOperaciones().stream()
                                    .filter(operacion -> operacion.getTipo()==1)
                                    .collect(Collectors.toMap(
                                            Operacion::getId,
                                            operacion -> operacion.getHerramienta().stream()
                                                    .collect(Collectors.groupingBy(
                                                            Herramienta::getId,
                                                            Collectors.counting()
                                                    ))
                                    ))

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

            Map<String,Long> piezasPrestamos = prestamos.stream()
                    .flatMap(operacion -> operacion.getHerramienta().stream())
                    .collect(Collectors.groupingBy(Herramienta::getId,Collectors.counting()));


            Map<String,Long> piezasDevoluciones = devoluciones.stream()
                    .flatMap(operacion -> operacion.getHerramienta().stream())
                    .collect(Collectors.groupingBy(Herramienta::getId,Collectors.counting()));


            workerstop.forEach((idWorker,listaOperaciones) -> {
                ListadoOperariosTopDto operarioTool = new ListadoOperariosTopDto();
                listaOperaciones.forEach((idOperacion,listaTools) -> {
                    listaTools.forEach((idTool,cantidad) -> {
                        operarioTool.setOperario(operarioRepositorio.findById(idWorker).get());
                        if (idTool == id){
                            if(operarioTool.getCantidad()!= null){
                                operarioTool.setCantidad(operarioTool.getCantidad()+cantidad);
                            }else{
                                operarioTool.setCantidad(cantidad);
                            }

                        }
                    });
                });

                if (operarioTool.getCantidad()!=null){
                    listaOperariosTool.add(operarioTool);
                }
            });

            listaOperariosTool.sort(new ComparadorListadoOperariosTopDto().reversed());

            kitsTool.forEach((idKit,listaTools) -> {
                ListadoKitsTopDto kitTool = new ListadoKitsTopDto();
                listaTools.forEach((idTool,cantidad) -> {
                    kitTool.setKit(kitRepositorio.findById(idKit).get());
                    if (idTool == id){
                        kitTool.setCantidad(cantidad);
                    }
                });

                listaKitsTool.add(kitTool);
            });

            listaKitsTool.sort(new ComparadorListadoKitsTopDto().reversed());

            resumen.setTotalOper((long) toolOperaciones.size());
            resumen.setOperPrestamos((long) prestamos.size());
            resumen.setOperDevoluciones((long) devoluciones.size());
            resumen.setPiezasPrestadas(piezasPrestamos.get(id));
            resumen.setPiezasDevueltas(piezasDevoluciones.get(id));
            resumen.setOperL30d((long) operL30d.size());
            resumen.setTotalOperarios((long) listaOperariosTool.size());
            resumen.setTotalKits((long) listaKitsTool.size());
            resumen.setListaUsoOperarios(listaOperariosTool);
            resumen.setListaUsoKits(listaKitsTool);

            return resumen;
        }
        return null;
    }


    public List<Operacion> listarOperacionesTool (String id){
        Optional<Herramienta> toolOper = repositorio.findById(id);
        if (toolOper.isPresent()){
            List<Operacion> operacions = toolOper.get().getOperaciones();
            List<Operacion> operacionesSinRepetir = new ArrayList<>();
            HashSet<Operacion> sinDobles = new HashSet<>();
            for(Operacion oper : operacions){
                if(sinDobles.add(oper)){
                    operacionesSinRepetir.add(oper);
                }
            }
            operacionesSinRepetir.sort(new ComparadorOperaciones().reversed());
            return operacionesSinRepetir;
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
