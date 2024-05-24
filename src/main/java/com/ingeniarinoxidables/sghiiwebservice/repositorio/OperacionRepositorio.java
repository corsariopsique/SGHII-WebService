package com.ingeniarinoxidables.sghiiwebservice.repositorio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Operacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperacionRepositorio extends JpaRepository<Operacion,String>{
}
