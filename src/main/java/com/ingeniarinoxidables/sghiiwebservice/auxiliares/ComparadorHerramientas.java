package com.ingeniarinoxidables.sghiiwebservice.auxiliares;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;

import java.util.Comparator;

public class ComparadorHerramientas implements Comparator<Herramienta> {

    @Override
    public int compare(Herramienta obj1, Herramienta obj2){
        return obj1.getFecha_in().compareTo(obj2.getFecha_in());
    }
}
