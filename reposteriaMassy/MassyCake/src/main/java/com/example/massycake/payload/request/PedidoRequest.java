package com.example.massycake.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoRequest {
    private String nombre;
    @NotBlank(message = "Debe especificar el tipo de Pedido")
    private String tipoPedido;
    private LocalDateTime fechaEntrega;

    private LocalDateTime fechaOrden;

    private float descuento;
    @NotBlank(message = "La descripcion no puede estar vacia.")
    private String descripcion;
    private String tipoEntrega;

    @NotNull(message = "Debes agregar una cotización")
    private Float costoTotal;
    private float inicial;

    @NotBlank(message = "Debes de seleccionar un cliente")
    private String cliente;

    @NotEmpty(message = "El detalle del pedido no puede estar vacío")
    private List<DetallePedidoRequest> detallePedido;

    @ModelAttribute
    public void setDefaultAttribute() {
        if (tipoEntrega == null) {
            tipoEntrega = "Tienda";
        }

    }
    public List<DetallePedidoRequest> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(List<DetallePedidoRequest> detallePedido) {
        this.detallePedido = detallePedido;
    }

    public float getInicial() {
        return inicial;
    }

    public void setInicial(float inicial) {
        this.inicial = inicial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Float getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Float costoTotal) {
        this.costoTotal = costoTotal;
    }
}
