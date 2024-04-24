package com.example.massycake.payload.response;

import com.example.massycake.entities.OrdenCompra;
import com.example.massycake.entities.Producto;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;

public class IngredienteRecetaResponse {
    private IngredienteResponse ingrediente;
    private float cantidad;
    private String unidadCantidad;

    public IngredienteResponse getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(IngredienteResponse ingrediente) {
        this.ingrediente = ingrediente;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadCantidad() {
        return unidadCantidad;
    }

    public void setUnidadCantidad(String unidadCantidad) {
        this.unidadCantidad = unidadCantidad;
    }
}
