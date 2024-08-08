package com.ingeniarinoxidables.sghiiwebservice.auxiliares;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListadoHerramientasTopDto;

import java.util.Comparator;

public class ComparadorListadoHerramientasTopDto implements Comparator<ListadoHerramientasTopDto> {

    @Override
    public int compare (ListadoHerramientasTopDto obj1, ListadoHerramientasTopDto obj2) {
        return obj1.getCantidad().compareTo(obj2.getCantidad());
    }
}
