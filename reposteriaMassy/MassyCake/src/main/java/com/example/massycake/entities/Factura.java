package com.example.massycake.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Factura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long facturaId;
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "detalle_factura",
            joinColumns = @JoinColumn(name = "factura_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<Producto> productos;
    @ManyToOne(optional = false)
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;
    @OneToMany(mappedBy = "factura", fetch = FetchType.LAZY)
    private List<ReciboPago> reciboPagos;
    @OneToMany(mappedBy = "factura", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DetalleFactura> detalleFacturas;
    @Column
    private Float total;
    @Column
    private boolean esACredito;
    @Column
    private LocalDateTime fecha;
    @Column
    private Float descuento;

    public Factura(Cliente cliente, List<Producto> productos, Empleado empleado, float descuento, boolean esACredito) {
        this.cliente = cliente;
        this.productos = productos;
        this.empleado = empleado;
        this.esACredito = esACredito;
        this.descuento = descuento;
        this.fecha = LocalDateTime.now();
        //TODO: CALCULAR EL TOTAL DE LA FACTURA
    }

    public Factura() {
        this.productos = new ArrayList<>();
        this.reciboPagos = new ArrayList<>();
        this.detalleFacturas = new ArrayList<>();
    }

    public List<DetalleFactura> getDetalleFacturas() {
        return detalleFacturas;
    }

    public void setDetalleFacturas(List<DetalleFactura> detalleFacturas) {
        this.detalleFacturas = detalleFacturas;
    }

    public long getFacturaId() {
        return facturaId;
    }
    public void setFacturaId(long facturaId) {
        this.facturaId = facturaId;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public List<Producto> getProductos() {
        return productos;
    }
    public void setProductos(List<Producto> productos) {
        this.productos.addAll(0, productos);
    }
    public Empleado getEmpleado() {
        return empleado;
    }
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    public Float getTotal() {
        return total;
    }
    public void setTotal(Float total) {
        this.total = total;
    }
    public boolean isEsACredito() {
        return esACredito;
    }
    public void setEsACredito(boolean esACredito) {
        this.esACredito = esACredito;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public List<ReciboPago> getReciboPagos() {
        return reciboPagos;
    }
    public void setReciboPagos(List<ReciboPago> reciboPagos) {
        this.reciboPagos = reciboPagos;
    }
    public Float getDescuento() {
        return descuento;
    }
    public void setDescuento(Float descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "facturaId=" + facturaId +
                ", cliente=" + cliente +
                ", productos=" + productos +
                ", empleado=" + empleado +
                ", total=" + total +
                ", esACredito=" + esACredito +
                ", fecha=" + fecha +
                ", descuento=" + descuento +
                '}';
    }

    public void addDetalle(DetalleFactura detalleFactura) {
        this.detalleFacturas.add(detalleFactura);
    }
}
