package com.ingeniarinoxidables.sghiiwebservice.repositorio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.ItemHerramienta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemHerramientaRepositorio extends JpaRepository<ItemHerramienta,Integer> {

    List<ItemHerramienta> findByEstado(Integer estado);

    @Query("SELECT t FROM itemherramienta t WHERE t.herramienta.id = :tool")
    List<ItemHerramienta> itemsByTool (@Param("tool")String tool);

    @Query("SELECT t FROM itemherramienta t WHERE t.id = :query")
    List<ItemHerramienta> searchItemToolsById(@Param("query") int query);

    @Query("SELECT t FROM itemherramienta t WHERE t.herramienta.id LIKE :query " +
            "OR t.herramienta.nombre LIKE :query")

    List<ItemHerramienta> searchItemToolsByTool(@Param("query") String query);

}
