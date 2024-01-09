package com.example.massycake.payload.request;

import com.example.massycake.entities.DetallePedido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoCabinaRequest {
    private String nombre;

    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaOrden;

    @NotBlank(message = "La descripción no puede estar vacía.")
    private String descripcion;
    @NotEmpty(message = "El detalle del pedido no puede estar vacío")
    private List<DetallePedidoRequest> detallePedido;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDateTime getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(LocalDateTime fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<DetallePedidoRequest> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(List<DetallePedidoRequest> detallePedido) {
        this.detallePedido = detallePedido;
    }
}
