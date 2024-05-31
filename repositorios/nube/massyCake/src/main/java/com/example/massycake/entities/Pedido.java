package com.example.massycake.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Entity
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pedidoId;
    @Column
    private String tipoPedido;
    @Column
    private String nombre;
    @Column
    private LocalDateTime fechaOrden;
    @Column
    private LocalDateTime fechaEntrega;
    @Column
    private LocalDateTime fechaDespachado;
    @Column
    private String estado;
    @Column
    private Float descuento;
    @Column
    private String descripcion;
    @Column
    private Float costoTotal;
    @Column
    private Float inicial;
    @Column
    private String metodoEntrega;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cliente cliente;
    @ManyToOne(optional = false)
    @JoinColumn(name = "empleado_id")
    @JsonIgnore
    private Empleado empleado;
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DetallePedido> detallePedido;

//    @ElementCollection(fetch = FetchType.LAZY)
//    @CollectionTable(name = "pedido_ingrediente")
//    private List<PedidoIngrediente> ingredientes;
    @Column(columnDefinition = "boolean default false")
    private boolean eliminado;

    public Pedido(String tipoPedido, String nombre, LocalDateTime fechaEntrega, String estado, Float descuento, String descripcion, String tipoEntrega, Cliente cliente, Empleado empleado) {
        this.tipoPedido = tipoPedido;
        this.nombre = nombre;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.descuento = descuento;
        this.descripcion = descripcion;
        this.metodoEntrega = tipoEntrega;
        this.cliente = cliente;
        this.empleado = empleado;
        this.detallePedido = new ArrayList<>();
        fechaOrden = LocalDateTime.now();
        costoTotal = calcularCostoTotal();
    }

    private Float calcularCostoTotal() {
        AtomicReference<Float> total = new AtomicReference<>(0f);

        detallePedido.forEach(detalle -> {
            total.updateAndGet(value -> value + detalle.getCosto());
        });

        return total.get();
    }

    public Pedido() {}

    public Float getInicial() {
        return inicial;
    }

    public void setInicial(Float inicial) {
        this.inicial = inicial;
    }

    public long getPedidoId() {
        return pedidoId;
    }
    public void setPedidoId(long pedidoId) {
        this.pedidoId = pedidoId;
    }
    public String getTipoPedido() {
        return tipoPedido;
    }
    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public LocalDateTime getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(LocalDateTime fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDateTime getFechaDespachado() {
        return fechaDespachado;
    }
    public void setFechaDespachado(LocalDateTime fechaDespachado) {
        this.fechaDespachado = fechaDespachado;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Float getDescuento() {
        return descuento;
    }
    public void setDescuento(Float descuento) {
        this.descuento = descuento;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getMetodoEntrega() {
        return metodoEntrega;
    }
    public void setMetodoEntrega(String metodoEntrega) {
        this.metodoEntrega = metodoEntrega;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Empleado getEmpleado() {
        return empleado;
    }
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<DetallePedido> getDetallePedido() {
        return detallePedido;
    }
    public void setDetallePedido(List<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
    }
    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Float getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Float costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

//    public List<PedidoIngrediente> getIngredientes() {
//        return ingredientes;
//    }

//    public void setIngredientes(List<PedidoIngrediente> ingredientes) {
//        this.ingredientes = ingredientes;
//    }
//    public void addIngrediente(PedidoIngrediente newIngrediente) {
//        this.ingredientes.add(newIngrediente);
//    }
    public void addDetalle(DetallePedido detalle) {
        this.detallePedido.add(detalle);
    }


}
