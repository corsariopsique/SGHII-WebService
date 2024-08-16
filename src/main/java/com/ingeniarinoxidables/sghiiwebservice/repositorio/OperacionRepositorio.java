package com.ingeniarinoxidables.sghiiwebservice.repositorio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OperacionRepositorio extends JpaRepository<Operacion,String>{

    @Query("SELECT t FROM asignacion_devolucion t WHERE t.fecha_operacion BETWEEN :startDate AND :endDate")
    List<Operacion> operacionesRangoFecha(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(t) FROM asignacion_devolucion t WHERE SIZE(t.kit) = 0 AND t.fecha_operacion BETWEEN :startDate AND :endDate")
    Long conteoOperFechaTools(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(t) FROM asignacion_devolucion t WHERE SIZE(t.herramienta) = 0 AND t.fecha_operacion BETWEEN :startDate AND :endDate")
    Long conteoOperFechaKits(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(o.operario) FROM asignacion_devolucion o GROUP BY o.operario")
    List<Object[]> promedioOperOperador();

    @Query("SELECT COUNT(o) FROM asignacion_devolucion o WHERE o.tipo = :tipo")
    Long contadorOperacionesTipo(@Param("tipo") int tipo);

    @Query("SELECT o FROM asignacion_devolucion o WHERE SIZE(o.herramienta) > 0")
    List<Operacion> findOperByTools ();

    @Query("SELECT t FROM asignacion_devolucion t WHERE SIZE(t.kit) > 0")
    List<Operacion> findOperByKits ();

    @Query("SELECT AVG(SIZE(o.herramienta)) FROM asignacion_devolucion o WHERE SIZE(o.herramienta) > 0")
    Double promedioToolsOper();

}
