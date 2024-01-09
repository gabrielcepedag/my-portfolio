package com.example.massycake.entities;

import java.util.UUID;

public class CarritoItem {

    private UUID id;
    private Producto producto;
    private int cantidad;

    public CarritoItem(UUID id, Producto producto) {
        this.id = id;
        this.producto = producto;
        this.cantidad = 1;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
