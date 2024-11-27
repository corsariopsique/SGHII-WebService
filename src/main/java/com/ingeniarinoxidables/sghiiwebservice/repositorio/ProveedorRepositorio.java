package com.ingeniarinoxidables.sghiiwebservice.repositorio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProveedorRepositorio extends JpaRepository<Proveedor,String> {
    List<Proveedor> findByEstado(Boolean estado);

    @Query("SELECT t FROM Proveedor t WHERE t.nombre LIKE :query OR t.id LIKE :query OR t.telefono LIKE :query " +
            "OR t.ciudad LIKE :query")
    List<Proveedor> searchSuplier(@Param("query") String query);


}
