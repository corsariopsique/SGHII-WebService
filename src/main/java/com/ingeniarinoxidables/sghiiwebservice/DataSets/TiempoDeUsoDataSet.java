package com.ingeniarinoxidables.sghiiwebservice.DataSets;

import com.ingeniarinoxidables.sghiiwebservice.auxiliares.ComparadorOperaciones;
import com.ingeniarinoxidables.sghiiwebservice.modelo.ItemHerramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import com.ingeniarinoxidables.sghiiwebservice.servicio.ItemHerramientaServicio;
import com.ingeniarinoxidables.sghiiwebservice.servicio.KitServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class TiempoDeUsoDataSet {

    @Autowired
    private ItemHerramientaServicio itemHerramientaServicio;

    @Autowired
    private KitServicio kitServicio;

    public long tiempoHerramientaP (String tool){

        List<ItemHerramienta> allItems = itemHerramientaServicio.listarItemsByTool(tool);
        long tiempoToolP = 0;

        for (ItemHerramienta item : allItems){
            tiempoToolP = tiempoToolP +
                    tiempoItemP(item.getId());
        }

        return tiempoToolP;
    }

    public long tiempoItemP (int id){

        long tiempoAcumuladoPItems = 0;

        List<Operacion> opersByItem = itemHerramientaServicio.listarOperacionesItem(id);

        List<Operacion> prestamosByItem = new java.util.ArrayList<>(opersByItem.stream()
                .filter(operacion -> (operacion.getTipo() == 1)).toList());

        List<Operacion> devolucionesByItem = new java.util.ArrayList<>(opersByItem.stream()
                .filter(operacion -> (operacion.getTipo() == 2)).toList());

        prestamosByItem.sort(new ComparadorOperaciones());
        devolucionesByItem.sort(new ComparadorOperaciones());

        Iterator<Operacion> iterPrestamos = prestamosByItem.iterator();
        Iterator<Operacion> iterDevoluciones = devolucionesByItem.iterator();

        LocalDate stateP = null;
        LocalDate stateD = null;

        while (iterPrestamos.hasNext() || iterDevoluciones.hasNext()){
            if(iterPrestamos.hasNext()){
                stateP = iterPrestamos.next().getFecha_operacion();
            }
            if(iterDevoluciones.hasNext()){
                stateD = iterDevoluciones.next().getFecha_operacion();
            }

            if(stateP != null && stateD != null){
                tiempoAcumuladoPItems = tiempoAcumuladoPItems +
                        ChronoUnit.DAYS.between(stateP,stateD);
            }

            if(stateD == null){
                tiempoAcumuladoPItems = tiempoAcumuladoPItems +
                        ChronoUnit.DAYS.between(stateP,LocalDate.now());
            }

            stateP = null;
            stateD = null;
        }

        return tiempoAcumuladoPItems;
    }

    public long tiempoKitP (String id){

        long tiempoAcumuladoPKits = 0;

        List<Operacion> opersByKit = kitServicio.listarOperacionesKits(id);

        List<Operacion> prestamosByKit = new java.util.ArrayList<>(opersByKit.stream()
                .filter(operacion -> (operacion.getTipo() == 1)).toList());

        List<Operacion> devolucionesByKit = new java.util.ArrayList<>(opersByKit.stream()
                .filter(operacion -> (operacion.getTipo() == 2)).toList());

        prestamosByKit.sort(new ComparadorOperaciones());
        devolucionesByKit.sort(new ComparadorOperaciones());

        Iterator<Operacion> iterPrestamos = prestamosByKit.iterator();
        Iterator<Operacion> iterDevoluciones = devolucionesByKit.iterator();

        LocalDate stateP = null;
        LocalDate stateD = null;

        while (iterPrestamos.hasNext() || iterDevoluciones.hasNext()){
            if(iterPrestamos.hasNext()){
                stateP = iterPrestamos.next().getFecha_operacion();
            }
            if(iterDevoluciones.hasNext()){
                stateD = iterDevoluciones.next().getFecha_operacion();
            }

            if(stateP != null && stateD != null){
                tiempoAcumuladoPKits = tiempoAcumuladoPKits +
                        ChronoUnit.DAYS.between(stateP,stateD);
            }

            if(stateD == null){
                tiempoAcumuladoPKits = tiempoAcumuladoPKits +
                        ChronoUnit.DAYS.between(stateP,LocalDate.now());
            }

            stateP = null;
            stateD = null;
        }

        return tiempoAcumuladoPKits;
    }
}
