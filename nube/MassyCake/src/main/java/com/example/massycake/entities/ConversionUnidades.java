package com.example.massycake.entities;

import jakarta.persistence.*;

@Entity

public class ConversionUnidades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long conversionUnidadesId;
//    @ManyToOne(optional = false, cascade = CascadeType.ALL)
//    @JoinColumn(name = "producto_id")
//    private Producto ingrediente;
    private String unidadOrigen;
    private String unidadEquivalente;
    private float factor;
    @Column(columnDefinition = "varchar(20) default 'cualquiera'")
    private String tipo;
    @Column(columnDefinition = "boolean default true")
    private boolean activa;

    public ConversionUnidades(String unidadOrigen, String unidadEquivalente, float factor) {
        this.unidadOrigen = unidadOrigen;
        this.unidadEquivalente = unidadEquivalente;
        this.factor = factor;
    }

    public ConversionUnidades() {}

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getConversionUnidadesId() {
        return conversionUnidadesId;
    }

    public void setConversionUnidadesId(long conversionUnidadesId) {
        this.conversionUnidadesId = conversionUnidadesId;
    }

    public String getUnidadOrigen() {
        return unidadOrigen;
    }

    public void setUnidadOrigen(String unidadOrigen) {
        this.unidadOrigen = unidadOrigen;
    }

    public String getUnidadEquivalente() {
        return unidadEquivalente;
    }

    public void setUnidadEquivalente(String unidadEquivalente) {
        this.unidadEquivalente = unidadEquivalente;
    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
