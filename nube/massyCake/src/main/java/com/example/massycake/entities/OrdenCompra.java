package com.example.massycake.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class OrdenCompra implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long OrdenCompraId;
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "producto_id")
    private Producto producto;
    @Column
    private Integer cantidad;
    @Column
    private String estado;
    @Column
    private Float precioIndividual;
    @Column
    private LocalDateTime fechaProcesada;
    @Column
    private LocalDateTime fechaGenerada;
    @Column
    private LocalDateTime fechaRecibida;
    @Column(columnDefinition = "boolean default false")
    private boolean eliminada;

    public OrdenCompra(Producto miProducto) {
        this.producto = miProducto;
        setEstadoGenerada();
    }

    public OrdenCompra() {}
    public Long getOrdenCompraId() {
        return OrdenCompraId;
    }
    public void setOrdenCompraId(Long ordenCompraId) {
        OrdenCompraId = ordenCompraId;
    }
    public Producto getMiProducto() {
        return producto;
    }
    public void setMiProducto(Producto miProducto) {
        this.producto = miProducto;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstadoGenerada() {
        this.estado = "Generada";
    }
    public void setEstadoProcesada() {
        this.estado = "Procesada";
    }
    public void setEstadoRecibida() {
        this.estado = "Recibida";
    }
    public float getPrecioIndividual() {
        return precioIndividual;
    }
    public void setPrecioIndividual(float precioIndividual) {
        this.precioIndividual = precioIndividual;
    }

    public void setPrecioIndividual(Float precioIndividual) {
        this.precioIndividual = precioIndividual;
    }

    public LocalDateTime getFechaProcesada() {
        return fechaProcesada;
    }

    public void setFechaProcesada(LocalDateTime fechaProcesada) {
        this.fechaProcesada = fechaProcesada;
    }

    public LocalDateTime getFechaGenerada() {
        return fechaGenerada;
    }

    public void setFechaGenerada(LocalDateTime fechaGenerada) {
        this.fechaGenerada = fechaGenerada;
    }

    public LocalDateTime getFechaRecibida() {
        return fechaRecibida;
    }

    public void setFechaRecibida(LocalDateTime fechaRecibida) {
        this.fechaRecibida = fechaRecibida;
    }

    public boolean isEliminada() {
        return eliminada;
    }
    public void setEliminada(boolean eliminada) {
        this.eliminada = eliminada;
    }
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "OrdenCompra{" +
                "OrdenCompraId=" + OrdenCompraId +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", estado='" + estado + '\'' +
                ", precioIndividual=" + precioIndividual +
                ", fechaProcesada=" + fechaProcesada +
                ", fechaGenerada=" + fechaGenerada +
                ", fechaRecibida=" + fechaRecibida +
                ", eliminada=" + eliminada +
                '}';
    }
}
