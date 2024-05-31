package com.example.massycake.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class OrdenCompraRequest {
    private long id;

    @Positive(message = "La cantidad debe ser mayor que 0")
    private int cantidad;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
