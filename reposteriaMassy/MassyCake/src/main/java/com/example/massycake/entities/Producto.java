package com.example.massycake.entities;

import com.example.massycake.payload.response.IngredienteResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "producto")
public class Producto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productoId;
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column
    private Integer cantidad;
    @Column
    private String categoria;
    @Column(columnDefinition = "float default null")
    private Float medida;
    @Column(name = "unidad_medida")
    private String unidadMedida;
    @Column(name = "disp_min")
    private Integer dispMin;
    @Column(updatable = false)
    private String tipo;
    @Column(columnDefinition = "boolean default false")
    private boolean eliminado;
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrdenCompra> ordenesCompras;
    @Column(columnDefinition = "float default 0.0")
    private Float descuento;
    @Column(columnDefinition = "float default 0.0")
    private Float inicial;
    @Column
    private String foto;

    @Column
    private Double precio;

//    @OneToMany(mappedBy = "ingrediente", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<ConversionUnidades> conversionUnidades;
    public Producto() {}

    @Override
    public String toString() {
        return "Producto{" +
                "productoId=" + productoId +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidad=" + cantidad +
                ", medida=" + medida +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", dispMin=" + dispMin +
                ", tipo='" + tipo + '\'' +
                ", inicial='" + inicial + '\'' +
                ", eliminado=" + eliminado +
                '}';
    }

    public Long getProductoId() {
        return productoId;
    }

    public Long getId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Float getMedida() {
        return medida;
    }

    public void setMedida(Float medida) {
        this.medida = medida;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Integer getDispMin() {
        return dispMin;
    }

    public void setDispMin(Integer dispMin) {
        this.dispMin = dispMin;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public List<OrdenCompra> getOrdenesCompras() {
        return ordenesCompras;
    }

    public void setOrdenesCompras(List<OrdenCompra> ordenesCompras) {
        this.ordenesCompras = ordenesCompras;
    }

    public Float getDescuento() {
        return descuento;
    }

    public void setDescuento(Float descuento) {
        this.descuento = descuento;
    }

    public Float getInicial() {
        return inicial;
    }

    public void setInicial(Float inicial) {
        this.inicial = inicial;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public IngredienteResponse getIngredienteResponse() {
        IngredienteResponse ing = new IngredienteResponse();
        ing.setProductoId(this.productoId);
        ing.setNombre(this.nombre);
        ing.setCantidad(this.cantidad);
        ing.setDescripcion(this.descripcion);
        ing.setEliminado(this.eliminado);
        ing.setTipo(this.tipo);
        ing.setDispMin(this.dispMin);
        ing.setMedida(this.medida);
        ing.setUnidadMedida(this.unidadMedida);

        return ing;
    }


}

