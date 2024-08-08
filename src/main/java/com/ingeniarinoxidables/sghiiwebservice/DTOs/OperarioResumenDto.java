package com.ingeniarinoxidables.sghiiwebservice.DTOs;

import java.util.HashSet;
import java.util.List;

public class OperarioResumenDto {

    private int operariosReg;
    private int operariosActivos;
    private int operariosDeBaja;
    private int operariosRoles;
    private HashSet<String> roles;

    public OperarioResumenDto() {
    }

    public int getOperariosReg() {
        return operariosReg;
    }

    public void setOperariosReg(int operariosReg) {
        this.operariosReg = operariosReg;
    }

    public int getOperariosActivos() {
        return operariosActivos;
    }

    public void setOperariosActivos(int operariosActivos) {
        this.operariosActivos = operariosActivos;
    }

    public int getOperariosDeBaja() {
        return operariosDeBaja;
    }

    public void setOperariosDeBaja(int operariosDeBaja) {
        this.operariosDeBaja = operariosDeBaja;
    }

    public int getOperariosRoles() {
        return operariosRoles;
    }

    public void setOperariosRoles(int operariosRoles) {
        this.operariosRoles = operariosRoles;
    }

    public HashSet<String> getRoles() {
        return roles;
    }

    public void setRoles(HashSet<String> roles) {
        this.roles = roles;
    }
}
