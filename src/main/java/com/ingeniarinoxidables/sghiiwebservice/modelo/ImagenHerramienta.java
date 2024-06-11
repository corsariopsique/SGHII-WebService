package com.ingeniarinoxidables.sghiiwebservice.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Arrays;
import java.util.Base64;

@Entity(name="imagen_herramienta")
public class ImagenHerramienta {

    @Id
    @Column(name= "idherramienta",nullable = false)
    private String id;

    @Column
    private byte[] image;

    @Column(nullable = false)
    private String image_name;

    public ImagenHerramienta(String id, byte[] image, String image_name) {
        this.id = id;
        this.image = image;
        this.image_name = image_name;
    }

    public ImagenHerramienta() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public boolean isValidBase64(byte[] base64) {
        try {
            Base64.getDecoder().decode(base64);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "ImagenHerramienta{" +
                "id='" + id + '\'' +
                ", image=" + Arrays.toString(image) +
                ", image_name='" + image_name + '\'' +
                '}';
    }
}
