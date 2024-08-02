package com.ingeniarinoxidables.sghiiwebservice.repositorio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperarioRepositorio extends JpaRepository<Operario,String> {
    List<Operario> findByEstado(Boolean estado);
}
