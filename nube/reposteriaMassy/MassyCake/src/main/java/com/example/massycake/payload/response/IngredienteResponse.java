package com.example.massycake.payload.response;

public class IngredienteResponse {
    private Long productoId;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private Float medida;
    private String unidadMedida;
    private Integer dispMin;
    private String tipo;
    private boolean eliminado;
    private Float inicial;

    public Long getProductoId() {
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public Float getInicial() {
        return inicial;
    }

    public void setInicial(Float inicial) {
        this.inicial = inicial;
    }
}
