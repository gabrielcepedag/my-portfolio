package com.example.massycake.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class IngredienteRequest {
    @NotBlank
    private String nombre;
    private String descripcion;
    @NotBlank
    private int cantidad;
    @NotBlank
    private float medida;
    @NotBlank
    private String unidadMedida;
    @NotBlank
    private int dispMin;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getMedida() {
        return medida;
    }

    public void setMedida(float medida) {
        this.medida = medida;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public int getDispMin() {
        return dispMin;
    }

    public void setDispMin(int dispMin) {
        this.dispMin = dispMin;
    }
}
