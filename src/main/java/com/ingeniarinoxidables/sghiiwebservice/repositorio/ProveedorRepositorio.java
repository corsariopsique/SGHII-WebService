package com.ingeniarinoxidables.sghiiwebservice.repositorio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProveedorRepositorio extends JpaRepository<Proveedor,String> {
    List<Proveedor> findByEstado(Boolean estado);
}
