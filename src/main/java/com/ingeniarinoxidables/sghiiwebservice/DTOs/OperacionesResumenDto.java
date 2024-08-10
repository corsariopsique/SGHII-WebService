package com.ingeniarinoxidables.sghiiwebservice.DTOs;

public class OperacionesResumenDto {

    private int totalOperaciones;
    private Long prestamos;
    private Long devoluciones;
    private Double promedioOperWorker;
    private Long operL30dTools;
    private Long operL30dKits;
    private Long workersOperPL7d;
    private Long workersOperDL7d;
    private Long operL7dP;
    private Long operL7dD;
    private Long toolsOperL7dP;
    private Long toolsOperL7dD;
    private Long kitsOperL7dP;
    private Long kitsOperL7dD;


    public OperacionesResumenDto() {
    }

    public Long getToolsOperL7dP() {
        return toolsOperL7dP;
    }

    public void setToolsOperL7dP(Long toolsOperL7dP) {
        this.toolsOperL7dP = toolsOperL7dP;
    }

    public Long getToolsOperL7dD() {
        return toolsOperL7dD;
    }

    public void setToolsOperL7dD(Long toolsOperL7dD) {
        this.toolsOperL7dD = toolsOperL7dD;
    }

    public Long getKitsOperL7dP() {
        return kitsOperL7dP;
    }

    public void setKitsOperL7dP(Long kitsOperL7dP) {
        this.kitsOperL7dP = kitsOperL7dP;
    }

    public Long getKitsOperL7dD() {
        return kitsOperL7dD;
    }

    public void setKitsOperL7dD(Long kitsOperL7dD) {
        this.kitsOperL7dD = kitsOperL7dD;
    }

    public Long getWorkersOperPL7d() {
        return workersOperPL7d;
    }

    public void setWorkersOperPL7d(Long workersOperPL7d) {
        this.workersOperPL7d = workersOperPL7d;
    }

    public Long getWorkersOperDL7d() {
        return workersOperDL7d;
    }

    public void setWorkersOperDL7d(Long workersOperDL7d) {
        this.workersOperDL7d = workersOperDL7d;
    }

    public Long getOperL7dP() {
        return operL7dP;
    }

    public void setOperL7dP(Long operL7dP) {
        this.operL7dP = operL7dP;
    }

    public Long getOperL7dD() {
        return operL7dD;
    }

    public void setOperL7dD(Long operL7dD) {
        this.operL7dD = operL7dD;
    }

    public Long getOperL30dTools() {
        return operL30dTools;
    }

    public void setOperL30dTools(Long operL30dTools) {
        this.operL30dTools = operL30dTools;
    }

    public Long getOperL30dKits() {
        return operL30dKits;
    }

    public void setOperL30dKits(Long operL30dKits) {
        this.operL30dKits = operL30dKits;
    }

    public int getTotalOperaciones() {
        return totalOperaciones;
    }

    public void setTotalOperaciones(int totalOperaciones) {
        this.totalOperaciones = totalOperaciones;
    }

    public Long getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(Long prestamos) {
        this.prestamos = prestamos;
    }

    public Long getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(Long devoluciones) {
        this.devoluciones = devoluciones;
    }

    public Double getPromedioOperWorker() {
        return promedioOperWorker;
    }

    public void setPromedioOperWorker(Double promedioOperWorker) {
        this.promedioOperWorker = promedioOperWorker;
    }

}
