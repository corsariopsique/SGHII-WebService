package com.ingeniarinoxidables.sghiiwebservice.repositorio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Operario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperarioRepositorio extends JpaRepository<Operario,String> {
}
