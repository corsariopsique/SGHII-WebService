package com.ingeniarinoxidables.sghiiwebservice.repositorio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Herramienta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HerramientaRepositorio extends JpaRepository<Herramienta,String> {
    List<Herramienta> findByEstado(Boolean estado);

}
