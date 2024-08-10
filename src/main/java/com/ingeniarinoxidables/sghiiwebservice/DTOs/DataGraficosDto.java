package com.ingeniarinoxidables.sghiiwebservice.DTOs;

public class DataGraficosDto {
    private String labels;
    private Long data;

    public DataGraficosDto() {
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }
}
