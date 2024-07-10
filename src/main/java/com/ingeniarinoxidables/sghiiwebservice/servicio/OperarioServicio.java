package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListaContenedor;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.OperarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OperarioServicio {

    @Autowired
    private OperarioRepositorio repositorio;

    public List<Operario> listarOperarios() { return repositorio.findAll(); }

    public Operario obtenerOperarioPorId(String id) { return repositorio.findById(id).orElse(null); }

    public Operario guardarOperario(Operario operario) { return repositorio.save(operario); }

    public void eliminarOperario(String id) { repositorio.deleteById(id); }

    private HashMap<String, Integer> frecuenciaListaTools (List<Herramienta> listado){
        HashMap<String,Integer> freqTools = new HashMap<String, Integer>();
        for(Herramienta itemP : listado){

            int count_freq = 0;

            if(!freqTools.containsKey(itemP.getId())){

                for(Herramienta itemPP : listado){
                    if(itemP.getId()==itemPP.getId()){
                        ++count_freq;
                    }
                }            
                freqTools.put(itemP.getId(),count_freq);
            }
        }
        return freqTools;
    }

    private HashMap<String, Integer> frecuenciaListaKits (List<Kit> listado){
        HashMap<String,Integer> freqKits = new HashMap<String, Integer>();
        for(Kit itemP : listado){

            int count_freq = 0;

            if(!freqKits.containsKey(itemP.getId())){

                for(Kit itemPP : listado) {
                    if (itemP.getId() == itemPP.getId()) {
                        ++count_freq;
                    }
                }
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
                    kitPrestados.add(operP.getKit());
                }
            }

            for(Operacion operD:operDevolucion){
                if(!operD.getHerramienta().isEmpty()){
                    toolsDevueltas.addAll(operD.getHerramienta());
                } else if (operD.getKit() != null) {
                kitDevueltos.add(operD.getKit());
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
                }else if (freqHerramientaD.get(item.getId())==null){
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
                } else if (freqKitD.get(item.getId())==null) {
                    for(int i = 0; i<freqKitP.get(item.getId());i++){
                        kits_PorEntregar.add(item);
                    }
                }
            }
        }

        return new ListaContenedor<>(herramientas_PorEntregar,kits_PorEntregar);
    }

}
