package com.ingeniarinoxidables.sghiiwebservice.DTOs;

public class PaqueteHerramientasOperacion {

    private String idOperacion;

    private String idTool;

    public String getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(String idOperacion) {
        this.idOperacion = idOperacion;
    }

    public String getIdTool() {
        return idTool;
    }

    public void setIdTool(String idTool) {
        this.idTool = idTool;
    }

    @Override
    public String toString() {
        return "PaqueteHerramientasOperacion{" +
                "idOperacion='" + idOperacion + '\'' +
                ", idTool='" + idTool + '\'' +
                '}';
    }
}
