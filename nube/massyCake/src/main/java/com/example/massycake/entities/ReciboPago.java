package com.example.massycake.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
public class ReciboPago implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ReciboPago_id;
    @Column
    private Float abonado;
    @Column
    private Float restante;
    @Column
    private String formaPago;
    @Column
    private Date fecha;
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "factura_id")
    private Factura factura;

    public ReciboPago(Factura factura, float abonado, String formaPago ) {
        this.abonado = abonado;
        this.formaPago = formaPago;
        this.factura = factura;
        this.fecha = new Date(new java.util.Date().getTime());
        //TODO: CALCULAR MONTO RESTANTE
    }
    public ReciboPago() {}

    public long getReciboPago_id() {
        return ReciboPago_id;
    }

    public void setReciboPago_id(long reciboPago_id) {
        ReciboPago_id = reciboPago_id;
    }

    public float getAbonado() {
        return abonado;
    }

    public void setAbonado(float abonado) {
        this.abonado = abonado;
    }

    public float getRestante() {
        return restante;
    }

    public void setRestante(float restante) {
        this.restante = restante;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @Override
    public String toString() {
        return "ReciboPago{" +
                "ReciboPago_id=" + ReciboPago_id +
                ", abonado=" + abonado +
                ", restante=" + restante +
                ", formaPago='" + formaPago + '\'' +
                ", fecha=" + fecha +
                ", factura=" + factura +
                '}';
    }
}
