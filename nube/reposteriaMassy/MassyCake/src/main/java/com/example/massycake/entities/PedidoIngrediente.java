package com.example.massycake.entities;

import com.example.massycake.payload.response.IngredienteRecetaResponse;
import jakarta.persistence.*;

@Embeddable
public class PedidoIngrediente {
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id")
    private Producto ingrediente;
    private float cantidad;
    private String unidadCantidad;

    public PedidoIngrediente(Producto ingrediente, float cantidad, String unidadCantidad) {
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
        this.unidadCantidad = unidadCantidad;
    }

    public PedidoIngrediente() {}

    public Producto getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Producto ingrediente) {
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

    public IngredienteRecetaResponse getIngredienteRecetaResponse(){
        IngredienteRecetaResponse ing = new IngredienteRecetaResponse();
        ing.setIngrediente(this.ingrediente.getIngredienteResponse());
        ing.setUnidadCantidad(this.unidadCantidad);
        ing.setCantidad(this.cantidad);

        return ing;
    }
}
