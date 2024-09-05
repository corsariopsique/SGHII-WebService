package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.ItemHerramientaResumenPorIdDto;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListaContenedor;
import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListadoOperariosTopDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorListadoOperariosTopDto;
import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorOperaciones;
import com.ingeniarinoxidables.sghiiwebservice.modelo.*;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.HerramientaRepositorio;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.ItemHerramientaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemHerramientaServicio {

    @Autowired
    private ItemHerramientaRepositorio repositorio;

    @Autowired
    private OperarioServicio operarioServicio;

    @Autowired
    private HerramientaRepositorio herramientaRepositorio;

    public List<ItemHerramienta> listarItemsHerramientas() { return repositorio.findAll(); }

    public List<ItemHerramienta> listarItemsHerramientasByEstado(Integer estado) { return repositorio.findByEstado(estado); }

    public List<ItemHerramienta> listarItemsByTool(String idTool) { return repositorio.itemsByTool(idTool); }

    public Optional<ItemHerramienta> obtenerItemHerramienta(int id) { return repositorio.findById(id); }

    public ItemHerramienta guardarItem (ItemHerramienta item) { return repositorio.save(item); }

    public ItemHerramienta eliminarItemHerramienta(int id) {
        Optional<ItemHerramienta> toolDeBaja = repositorio.findById(id);
        if (toolDeBaja.isPresent()){
            toolDeBaja.get().setEstado(1);
            toolDeBaja.get().setFecha_out(LocalDate.now());
            Optional<Herramienta> toolItem = herramientaRepositorio
                    .findById(toolDeBaja.get().getHerramienta().getId());
            if(toolItem.get().getCantidad_disponible()>1){
                toolItem.get().setCantidad_disponible(toolItem.get().getCantidad_disponible()-1);
                toolItem.get().setCantidad(toolItem.get().getCantidad()-1);
                herramientaRepositorio.save(toolItem.get());
            }else{
                return null;
            }
            return repositorio.save(toolDeBaja.get());
        }
        return null;
    }

    public List<Operacion> listarOperacionesItem (int id){
        Optional<ItemHerramienta> toolOper = repositorio.findById(id);
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

    public ItemHerramienta itemParaAsignar (List<ItemHerramienta> items){
        Optional<ItemHerramienta> itemAsignar = items.stream()
                .filter(itemHerramienta -> (itemHerramienta.getEstado() == 0))
                .reduce((itemHerramienta, itemHerramienta2) ->
                        itemHerramienta.getId() < itemHerramienta2.getId() ?
                                itemHerramienta : itemHerramienta2);
        return itemAsignar.orElse(null);
    }

    public ItemHerramientaResumenPorIdDto resumenPorId (int id) {

        ItemHerramientaResumenPorIdDto resumen = new ItemHerramientaResumenPorIdDto();

        Optional<ItemHerramienta> item = repositorio.findById(id);
        List<ListadoOperariosTopDto> listaOperariosItem = new ArrayList<>();

        if(item.isPresent()){
            List<Operacion> itemOper = listarOperacionesItem(id);

            Long prestamos = itemOper.stream()
                    .filter(operacion -> (operacion.getTipo()==1)).count();

            Long devoluciones = itemOper.stream()
                    .filter(operacion -> (operacion.getTipo()==2)).count();

            List<Kit> itemKits = item.get().getKits();

            Map <Operario,Long> itemOperario = itemOper.stream()
                    .filter(operacion -> (operacion.getTipo()==1))
                    .collect(Collectors.toMap(
                            Operacion::getOperario,
                            operacion -> operacion.getHerramienta().stream()
                                    .filter(itemHerramienta -> itemHerramienta==item.get()).count(),
                            Long::sum

                    ));

            itemOperario.forEach((worker,cantidad) -> {
                ListadoOperariosTopDto workerTop = new ListadoOperariosTopDto();
                workerTop.setOperario(worker);
                workerTop.setCantidad(cantidad);
                listaOperariosItem.add(workerTop);
            });

            listaOperariosItem.sort(new ComparadorListadoOperariosTopDto().reversed());

            if(item.get().getEstado()==1){
                if(item.get().getKits().isEmpty()){
                    List<Operario> workers = operarioServicio.listarOperariosPorEstado(false);
                    for(Operario worker : workers){
                        ListaContenedor itemsPrestados = operarioServicio.herramientasPrestamoActivo(worker.getId());
                        if(itemsPrestados.getHerramientas().contains(item.get())){
                            resumen.setUbicacionWorker(worker);
                        }
                    }
                }else{
                    resumen.setUbicacionKit(item.get().getKits().get(0));
                }
            }

            resumen.setItemKits(itemKits);
            resumen.setTotalOper((long) itemOper.size());
            resumen.setPrestamos(prestamos);
            resumen.setDevoluciones(devoluciones);
            resumen.setItemWorkers(listaOperariosItem);

            return resumen;
        }

        return null;
    }

}
