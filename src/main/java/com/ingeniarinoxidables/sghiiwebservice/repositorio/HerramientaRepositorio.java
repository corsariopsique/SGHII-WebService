package com.ingeniarinoxidables.sghiiwebservice.repositorio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HerramientaRepositorio extends JpaRepository<Herramienta,String> {
    List<Herramienta> findByEstado(Boolean estado);

    @Query("SELECT t FROM Herramienta t WHERE t.fecha_in BETWEEN :startDate AND :endDate")
    List<Herramienta> listaHeramientaInRangoDate (@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT t FROM Herramienta t WHERE t.cantidad_disponible <= 2")
    List<Herramienta> listarHerramientaEscasa();


    // metodo a revisar por implementacion itemHerramienta
    @Query("SELECT SUM(t.cantidad) FROM Herramienta t WHERE t.estado=false ")
    Long piezasTotalesActivas();

    // metodo a revisar por implementacion itemHerramienta
    @Query("SELECT SUM(t.cantidad) FROM Herramienta t ")
    Long piezasTotales();

    // metodo a revisar por implementacion itemHerramienta
    @Query("SELECT SUM(t.cantidad_kits) FROM Herramienta t WHERE t.estado=false ")
    Long piezasKits();

    @Query("SELECT SUM(t.cantidad_disponible) FROM Herramienta t WHERE t.estado=false ")
    Long piezasDisponibles();

    @Query("SELECT COUNT(t) FROM Herramienta t WHERE t.estado=false ")
    Long herramientasActivas();

    @Query("SELECT COUNT(t) FROM Herramienta t ")
    Long herramientasTodas();

}
