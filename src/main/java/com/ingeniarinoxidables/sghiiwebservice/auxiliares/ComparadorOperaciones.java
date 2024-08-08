package com.ingeniarinoxidables.sghiiwebservice.auxiliares;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;

import java.util.Comparator;

public class ComparadorOperaciones implements Comparator<Operacion> {

    @Override
    public int compare(Operacion obj1, Operacion obj2) {
        return obj1.getFecha_operacion().compareTo(obj2.getFecha_operacion());
    }
}
