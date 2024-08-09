package com.ingeniarinoxidables.sghiiwebservice.repositorio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OperacionRepositorio extends JpaRepository<Operacion,String>{

    @Query("SELECT COUNT(t) FROM asignacion_devolucion t WHERE t.fecha_operacion BETWEEN :startDate AND :endDate")
    Long conteoOperacionesFecha(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(t) FROM asignacion_devolucion t WHERE SIZE(t.kit) = 0 AND t.fecha_operacion BETWEEN :startDate AND :endDate")
    Long conteoOperFechaTools(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(t) FROM asignacion_devolucion t WHERE SIZE(t.herramienta) = 0 AND t.fecha_operacion BETWEEN :startDate AND :endDate")
    Long conteoOperFechaKits(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(o.operario) FROM asignacion_devolucion o GROUP BY o.operario")
    List<Object[]> promedioOperOperador();

    @Query("SELECT COUNT(o) FROM asignacion_devolucion o WHERE o.tipo = :tipo")
    Long contadorOperacionesTipo(@Param("tipo") int tipo);

    @Query("SELECT COUNT(o) FROM asignacion_devolucion o WHERE SIZE(o.herramienta) > 0")
    Long findOperByTools ();

    @Query("SELECT COUNT(t) FROM asignacion_devolucion t WHERE SIZE(t.kit) > 0")
    Long findOperByKits ();

    @Query("SELECT AVG(SIZE(o.herramienta)) FROM asignacion_devolucion o WHERE SIZE(o.herramienta) > 0")
    Double promedioToolsOper();

}
