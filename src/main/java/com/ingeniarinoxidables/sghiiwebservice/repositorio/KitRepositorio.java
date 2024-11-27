package com.ingeniarinoxidables.sghiiwebservice.repositorio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Kit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KitRepositorio extends JpaRepository<Kit,String> {
    List<Kit> findByEstado(Boolean estado);

    @Query("SELECT t FROM Kit t WHERE t.nombre LIKE :query OR t.id LIKE :query OR t.rol LIKE :query")
    List<Kit> searchKits(@Param("query") String query);

}
