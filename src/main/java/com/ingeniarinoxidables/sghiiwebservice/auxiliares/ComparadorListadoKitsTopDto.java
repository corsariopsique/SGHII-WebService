package com.ingeniarinoxidables.sghiiwebservice.auxiliares;

import com.ingeniarinoxidables.sghiiwebservice.DTOs.ListadoKitsTopDto;

import java.util.Comparator;

public class ComparadorListadoKitsTopDto implements Comparator<ListadoKitsTopDto> {

    @Override
    public int compare (ListadoKitsTopDto obj1, ListadoKitsTopDto obj2){
        return obj1.getCantidad().compareTo(obj2.getCantidad());
    }
}
