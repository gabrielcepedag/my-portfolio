package com.example.massycake.payload.request;

import com.example.massycake.entities.Producto;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class RecetaRequest {
    @NotBlank
    private String nombre;
    private String descripcion;
    @NotBlank
    private int minutosPreparacion;
    @NotBlank
    private int porcionResultante;
    @NotBlank
    private String uniPorcionResultante;

    private List<String> pasos;

    private List<Producto> ingredientes;

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

    public int getMinutosPreparacion() {
        return minutosPreparacion;
    }

    public void setMinutosPreparacion(int minutosPreparacion) {
        this.minutosPreparacion = minutosPreparacion;
    }

    public String getUniPorcionResultante() {
        return uniPorcionResultante;
    }
    public void setUniPorcionResultante(String uniPorcionResultante) {
        this.uniPorcionResultante = uniPorcionResultante;
    }
    public int getPorcionResultante() {
        return porcionResultante;
    }
    public void setPorcionResultante(int porcionResultante) {
        this.porcionResultante = porcionResultante;
    }
    public List<String> getPasos() {
        return pasos;
    }
    public void setPasos(List<String> pasos) {
        this.pasos = pasos;
    }

    public List<Producto> getIngredientes() {
        return ingredientes;
    }
    public void setIngredientes(List<Producto> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
