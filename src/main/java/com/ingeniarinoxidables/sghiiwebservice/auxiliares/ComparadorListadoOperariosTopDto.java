package com.ingeniarinoxidables.sghiiwebservice.auxiliares;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListadoOperariosTopDto;

import java.util.Comparator;

public class ComparadorListadoOperariosTopDto implements Comparator<ListadoOperariosTopDto> {

    @Override
    public int compare (ListadoOperariosTopDto obj1, ListadoOperariosTopDto obj2){
        return obj1.getCantidad().compareTo(obj2.getCantidad());
    }
}
