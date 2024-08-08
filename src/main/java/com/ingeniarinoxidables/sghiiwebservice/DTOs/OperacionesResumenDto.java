package com.ingeniarinoxidables.sghiiwebservice.DTOs;

public class OperacionesResumenDto {

    private int totalOperaciones;
    private Long prestamos;
    private Long devoluciones;
    private Double toolMeanOperacion;
    private Long operTool;
    private Long operKit;
    private Double promedioOperWorker;
    private Long operDay;
    private Long operWeek;
    private Long operMonth;

    public OperacionesResumenDto() {
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

    public Double getToolMeanOperacion() {
        return toolMeanOperacion;
    }

    public void setToolMeanOperacion(Double toolMeanOperacion) {
        this.toolMeanOperacion = toolMeanOperacion;
    }

    public Double getPromedioOperWorker() {
        return promedioOperWorker;
    }

    public void setPromedioOperWorker(Double promedioOperWorker) {
        this.promedioOperWorker = promedioOperWorker;
    }

    public Long getOperTool() {
        return operTool;
    }

    public void setOperTool(Long operTool) {
        this.operTool = operTool;
    }

    public Long getOperKit() {
        return operKit;
    }

    public void setOperKit(Long operKit) {
        this.operKit = operKit;
    }

    public Long getOperDay() {
        return operDay;
    }

    public void setOperDay(Long operDay) {
        this.operDay = operDay;
    }

    public Long getOperWeek() {
        return operWeek;
    }

    public void setOperWeek(Long operWeek) {
        this.operWeek = operWeek;
    }

    public Long getOperMonth() {
        return operMonth;
    }

    public void setOperMonth(Long operMonth) {
        this.operMonth = operMonth;
    }
}
