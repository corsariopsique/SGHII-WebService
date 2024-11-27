package com.ingeniarinoxidables.sghiiwebservice.repositorio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OperarioRepositorio extends JpaRepository<Operario,String> {
    List<Operario> findByEstado(Boolean estado);

    @Query("SELECT COUNT(o) FROM asignacion_devolucion o WHERE o.tipo = :tipo AND o.operario.id = :id")
    Long contadorOperWorker(@Param("tipo") int tipo, @Param("id")String id);

    @Query("SELECT COUNT(t) FROM asignacion_devolucion t WHERE t.operario.id = :id AND t.fecha_operacion BETWEEN :startDate AND :endDate")
    Long conteoOperacionesFecha(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("id") String id);

    @Query("SELECT t FROM Operario t WHERE t.nombre LIKE :query OR t.id LIKE :query OR t.email LIKE :query " +
            "OR t.telefono LIKE :query OR t.rol LIKE :query")
    List<Operario> searchWorkers(@Param("query") String query);



}
