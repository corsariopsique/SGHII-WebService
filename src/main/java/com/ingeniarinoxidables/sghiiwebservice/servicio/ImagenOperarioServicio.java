package com.ingeniarinoxidables.sghiiwebservice.servicio;

import com.ingeniarinoxidables.sghiiwebservice.modelo.ImagenOperario;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.ImagenOperarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImagenOperarioServicio {

    @Autowired
    private ImagenOperarioRepositorio repositorio;

    public Optional<ImagenOperario> obtenerImagenOPorId(String id) {return repositorio.findById(id); }

    public ImagenOperario guardarImagenO(ImagenOperario imagenOperario) { return repositorio.save((imagenOperario)); }

    public void eliminarImagenO(String id) { repositorio.deleteById(id); }

}
