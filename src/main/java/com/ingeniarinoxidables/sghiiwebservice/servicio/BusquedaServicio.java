package com.ingeniarinoxidables.sghiiwebservice.servicio;


import com.ingeniarinoxidables.sghiiwebservice.modelo.*;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusquedaServicio {

    @Autowired
    private HerramientaRepositorio herramientaRepositorio;

    @Autowired
    private ItemHerramientaRepositorio itemHerramientaRepositorio;

    @Autowired
    private KitRepositorio kitRepositorio;

    @Autowired
    private OperacionRepositorio operacionRepositorio;

    @Autowired
    private OperarioRepositorio operarioRepositorio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    public Map<String, Object> resultadoBusqueda (String query){

        Map<String, Object> results = new HashMap<>();

        List<Herramienta> tools = herramientaRepositorio.searchTools(query);
        results.put("tools",tools);

        List<ItemHerramienta> items = itemHerramientaRepositorio.searchItemToolsByTool(query);
        results.put("items", items);

        try{
            List<ItemHerramienta> itemsById = itemHerramientaRepositorio.searchItemToolsById(Integer.parseInt(query));
            results.put("items", itemsById);
        }catch (Exception ignored){}

        List<Kit> kits = kitRepositorio.searchKits(query);
        results.put("kits", kits);

        List<Operacion> operaciones = operacionRepositorio.searchOperaciones(query);
        results.put("operaciones",operaciones);

        List<Operario> operarios = operarioRepositorio.searchWorkers(query);
        results.put("operarios", operarios);

        List<Proveedor> proveedores = proveedorRepositorio.searchSuplier(query);
        results.put("proveedores", proveedores);

        return results;

    }
}
