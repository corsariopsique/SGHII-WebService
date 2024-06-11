package com.ingeniarinoxidables.sghiiwebservice.servicio;


import com.ingeniarinoxidables.sghiiwebservice.modelo.ImagenHerramienta;
import com.ingeniarinoxidables.sghiiwebservice.repositorio.ImagenHerramientaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenHerramientaServicio {

    @Autowired
    private ImagenHerramientaRepositorio repositorio;

    public Optional<ImagenHerramienta> obtenerImagenHPorId(String id) { return repositorio.findById(id); }

    public ImagenHerramienta guardarImagenH(ImagenHerramienta imagenHerramienta) { return repositorio.save(imagenHerramienta); }

    public void eliminarImagenH (String id) { repositorio.deleteById(id); }
}
