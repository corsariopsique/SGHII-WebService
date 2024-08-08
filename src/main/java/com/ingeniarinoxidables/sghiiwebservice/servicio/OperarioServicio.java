package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.*;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorListadoHerramientasTopDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorListadoKitsTopDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorOperaciones;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.HerramientaRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.KitRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OperarioServicio {

    @Autowired
    private OperarioRepositorio repositorio;

    @Autowired
    private HerramientaRepositorio repositorioTools;

    @Autowired
    private KitRepositorio repositorioKits;

    public List<Operario> listarOperarios() { return repositorio.findAll(); }

    public List<Operario> listarOperariosPorEstado(Boolean estado){
        return repositorio.findByEstado(estado);
    }

    public Operario obtenerOperarioPorId(String id) { return repositorio.findById(id).orElse(null); }

    public Operario guardarOperario(Operario operario) { return repositorio.save(operario); }

    public Operario eliminarOperario(String id) {
        Optional<Operario> workerDeBaja = repositorio.findById(id);
        if(workerDeBaja.isPresent()){
            workerDeBaja.get().setEstado(true);
            workerDeBaja.get().setFecha_out(LocalDate.now());
            return repositorio.save(workerDeBaja.get());
        }
        return null;
    }

    public List<Operacion> operacionesWorker(String id){
        Optional<Operario> workerOper = repositorio.findById(id);
        if(workerOper.isPresent()){
            List<Operacion> operaciones = workerOper.get().getOperaciones();
            operaciones.sort(new ComparadorOperaciones().reversed());
            return  operaciones;
        }
        return null;
    }

    public OperarioResumenDto resumen(){
        OperarioResumenDto resumen = new OperarioResumenDto();
        List<Operario> todos = repositorio.findAll();
        HashSet<String> roles = new HashSet<>();
        resumen.setOperariosReg(todos.size());
        int contActivos = 0;
        for (Operario worker : todos){
            if(!worker.getEstado()){
                ++contActivos;
            }
            roles.add(worker.getRol());
        }
        resumen.setOperariosActivos(contActivos);
        resumen.setOperariosDeBaja(todos.size()-contActivos);
        resumen.setOperariosRoles(roles.size());
        resumen.setRoles(roles);
        return resumen;
    }

    public OperarioResumenPorIdDto resumenPorID (String id){
        OperarioResumenPorIdDto resumenWorker = new OperarioResumenPorIdDto();
        List<ListadoHerramientasTopDto> listaToolsMax = new ArrayList<>();
        List<ListadoKitsTopDto> listaKitsMax = new ArrayList<>();
        Optional<Operario> worker = repositorio.findById(id);
        if (worker.isPresent()){
            List<Operacion> operWorker = operacionesWorker(id);

            Map<String, Long> herramientaContador = operWorker.stream()
                    .filter(operacion -> (operacion.getTipo()==1))
                    .flatMap(operacion -> operacion.getHerramienta().stream())
                    .collect(Collectors.groupingBy(herramienta -> herramienta.getId(), Collectors.counting()));

            Map<String,Long> kitContador = operWorker.stream()
                    .filter(operacion -> (operacion.getTipo()==1))
                    .flatMap((operacion -> operacion.getKit().stream()))
                    .collect(Collectors.groupingBy(kit -> kit.getId(),Collectors.counting()));


            herramientaContador.forEach((idTool,cantidad) -> {
                ListadoHerramientasTopDto elementoLista = new ListadoHerramientasTopDto();
                elementoLista.setTool(repositorioTools.findById(idTool).get());
                elementoLista.setCantidad(cantidad);
                listaToolsMax.add(elementoLista);
            });

            kitContador.forEach((idKit,cantidad) -> {
                ListadoKitsTopDto elementoLista = new ListadoKitsTopDto();
                elementoLista.setKit(repositorioKits.findById(idKit).get());
                elementoLista.setCantidad(cantidad);
                listaKitsMax.add(elementoLista);
            });

            listaToolsMax.sort(new ComparadorListadoHerramientasTopDto().reversed());
            listaKitsMax.sort(new ComparadorListadoKitsTopDto().reversed());

            resumenWorker.setTotalOper(operWorker.size());
            resumenWorker.setPrestamos(repositorio.contadorOperWorker(1,id));
            resumenWorker.setDevoluciones(repositorio.contadorOperWorker(2,id));
            resumenWorker.setOperL30d(repositorio.conteoOperacionesFecha(LocalDate.now().minusMonths(1),LocalDate.now(),id));
            resumenWorker.setListaUsoTools(listaToolsMax);
            resumenWorker.setListaUsoKits(listaKitsMax);
            resumenWorker.setTotalKits(kitContador.size());
            resumenWorker.setTotalTools(herramientaContador.size());

            return resumenWorker;
        }

        return null;
    }

    private HashMap<String, Integer> frecuenciaListaTools (List<Herramienta> listado){
        HashMap<String,Integer> freqTools = new HashMap<String, Integer>();
        for(Herramienta itemP : listado){

            if(!freqTools.containsKey(itemP.getId())){
                int count_freq = Collections.frequency(listado,itemP);
                freqTools.put(itemP.getId(),count_freq);
            }
        }

        return freqTools;
    }

    private HashMap<String, Integer> frecuenciaListaKits (List<Kit> listado){
        HashMap<String,Integer> freqKits = new HashMap<String, Integer>();
        for(Kit itemP : listado){

            if(!freqKits.containsKey(itemP.getId())){
                int count_freq = Collections.frequency(listado,itemP);
                freqKits.put(itemP.getId(),count_freq);
            }
        }
        return freqKits;
    }

    public ListaContenedor<Herramienta,Kit> herramientasPrestamoActivo(String idOperario){

        Optional<Operario> workerOpcional= repositorio.findById(idOperario);
        List<Operacion> operPrestamo = new ArrayList<>();
        List<Operacion> operDevolucion = new ArrayList<>();
        List<Herramienta> toolsPrestadas = new ArrayList<>();
        List<Herramienta> toolsDevueltas = new ArrayList<>();
        List<Kit> kitPrestados = new ArrayList<>();
        List<Kit> kitDevueltos = new ArrayList<>();
        List<Herramienta> herramientas_PorEntregar = new ArrayList<>();
        List<Kit> kits_PorEntregar = new ArrayList<>();

        if(workerOpcional.isPresent()){

            Operario workerTools = workerOpcional.get();
            List<Operacion> operacionesWorker = workerTools.getOperaciones();

            for(Operacion oper:operacionesWorker){
                if(oper.getTipo()==1){
                    operPrestamo.add(oper);
                } else if (oper.getTipo()==2) {
                    operDevolucion.add(oper);
                }
            }

            for(Operacion operP:operPrestamo){
                if(!operP.getHerramienta().isEmpty()){
                    toolsPrestadas.addAll(operP.getHerramienta());
                } else if (operP.getKit() != null) {
                    kitPrestados.addAll(operP.getKit());
                }
            }

            for(Operacion operD:operDevolucion){
                if(!operD.getHerramienta().isEmpty()){
                    toolsDevueltas.addAll(operD.getHerramienta());
                } else if (operD.getKit() != null) {
                kitDevueltos.addAll(operD.getKit());
                }
            }

            HashMap<String,Integer> freqHerramientaP = frecuenciaListaTools(toolsPrestadas);
            HashMap<String,Integer> freqKitP = frecuenciaListaKits(kitPrestados);
            HashMap<String,Integer> freqHerramientaD = frecuenciaListaTools(toolsDevueltas);
            HashMap<String,Integer> freqKitD = frecuenciaListaKits(kitDevueltos);

            for(Herramienta item : toolsPrestadas){
                if(freqHerramientaD.get(item.getId())!=null && !herramientas_PorEntregar.contains(item)){
                    int countTool = freqHerramientaP.get(item.getId())-freqHerramientaD.get(item.getId());
                    for(int i = 0; i<countTool; i++){
                        herramientas_PorEntregar.add(item);
                    }
                }else if (freqHerramientaD.get(item.getId())==null && !herramientas_PorEntregar.contains(item)){
                    for(int i = 0; i<freqHerramientaP.get(item.getId()); i++) {
                        herramientas_PorEntregar.add(item);
                    }
                }
            }

            for(Kit item : kitPrestados){
                if(freqKitD.get(item.getId())!=null && !kits_PorEntregar.contains(item)){
                    int countKit = freqKitP.get(item.getId())-freqKitD.get(item.getId());
                    for(int i = 0; i<countKit;i++){
                        kits_PorEntregar.add(item);
                    }
                } else if (freqKitD.get(item.getId())==null && !kits_PorEntregar.contains(item)) {
                    for(int i = 0; i<freqKitP.get(item.getId());i++){
                        kits_PorEntregar.add(item);
                    }
                }
            }
        }

        return new ListaContenedor<>(herramientas_PorEntregar,kits_PorEntregar);
    }

}
