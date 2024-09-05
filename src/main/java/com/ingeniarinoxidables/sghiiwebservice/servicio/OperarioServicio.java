package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.*;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorListadoHerramientasTopDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorListadoKitsTopDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorOperaciones;
import com.ingeniarinoxidables.sghiiwebservice.modelo.*;
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

    // metodo a revisar por implementacion itemHerramienta
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



    // metodo a revisar por implementacion itemHerramienta
    public OperarioResumenPorIdDto resumenPorID (String id){
        OperarioResumenPorIdDto resumenWorker = new OperarioResumenPorIdDto();
        List<ListadoHerramientasTopDto> listaToolsMax = new ArrayList<>();
        List<ListadoKitsTopDto> listaKitsMax = new ArrayList<>();
        Optional<Operario> worker = repositorio.findById(id);
        if (worker.isPresent()){
            List<Operacion> operWorker = operacionesWorker(id);

            Map<Herramienta, Long> herramientaContador = operWorker.stream()
                    .filter(operacion -> (operacion.getTipo()==1))
                    .flatMap(operacion -> operacion.getHerramienta().stream())
                    .collect(Collectors.groupingBy(ItemHerramienta::getHerramienta, Collectors.counting()));


            Map<String,Long> kitContador = operWorker.stream()
                    .filter(operacion -> (operacion.getTipo()==1))
                    .flatMap((operacion -> operacion.getKit().stream()))
                    .collect(Collectors.groupingBy(Kit::getId,Collectors.counting()));


            herramientaContador.forEach((tool,cantidad) -> {
                ListadoHerramientasTopDto elementoLista = new ListadoHerramientasTopDto();
                elementoLista.setTool(tool);
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

    private HashMap<Integer, Long> frecuenciaListaTools (List<Operacion> listado){
        HashMap<Integer,Long> listadoItemsFreq = new HashMap<>();

        Map<Integer, Long> listaItemsFreq = listado.stream()
                .flatMap(operacion -> operacion.getHerramienta().stream())
                .collect(Collectors.groupingBy(
                        ItemHerramienta::getId,
                        Collectors.counting()
                ));

        listadoItemsFreq.putAll(listaItemsFreq);

        return listadoItemsFreq ;
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


    // metodo a revisar por implementacion itemHerramienta
    public ListaContenedor<ItemHerramienta,Kit> herramientasPrestamoActivo(String idOperario){

        Optional<Operario> workerOpcional= repositorio.findById(idOperario);
        List<Operacion> operPrestamo = new ArrayList<>();
        List<Operacion> operDevolucion = new ArrayList<>();
        List<Kit> kitPrestados = new ArrayList<>();
        List<Kit> kitDevueltos = new ArrayList<>();
        List<ItemHerramienta> itemsPrestados = new ArrayList<>();
        List<ItemHerramienta> herramientas_PorEntregar = new ArrayList<>();
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
                if(!operP.getKit().isEmpty()){
                    kitPrestados.addAll(operP.getKit());
                } else if (!operP.getHerramienta().isEmpty()) {
                    itemsPrestados.addAll(operP.getHerramienta());
                }
            }

            for(Operacion operD:operDevolucion){
                if(!operD.getKit().isEmpty()){
                    kitDevueltos.addAll(operD.getKit());
                }
            }

            HashMap<Integer,Long> freqHerramientaP = frecuenciaListaTools(operPrestamo);
            HashMap<String,Integer> freqKitP = frecuenciaListaKits(kitPrestados);
            HashMap<Integer,Long> freqHerramientaD = frecuenciaListaTools(operDevolucion);
            HashMap<String,Integer> freqKitD = frecuenciaListaKits(kitDevueltos);

            for(ItemHerramienta item : itemsPrestados){
                if(freqHerramientaD.get(item.getId())!=null && !herramientas_PorEntregar.contains(item)){
                    long countTool = freqHerramientaP.get(item.getId())-freqHerramientaD.get(item.getId());
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
