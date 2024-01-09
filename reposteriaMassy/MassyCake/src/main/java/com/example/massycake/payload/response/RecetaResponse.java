package com.example.massycake.payload.response;

import com.example.massycake.entities.IngredienteReceta;
import com.example.massycake.utils.StringListConverter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class RecetaResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private int minutosPreparacion;
    private int porcionResultante;
    private String uniPorcionResultante;
    private boolean eliminado;
    private List<IngredienteRecetaResponse> ingredientes = new ArrayList<>();
    private List<String> pasos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getMinutosPreparacion() {
        return minutosPreparacion;
    }

    public void setMinutosPreparacion(int minutosPreparacion) {
        this.minutosPreparacion = minutosPreparacion;
    }

    public int getPorcionResultante() {
        return porcionResultante;
    }

    public void setPorcionResultante(int porcionResultante) {
        this.porcionResultante = porcionResultante;
    }

    public String getUniPorcionResultante() {
        return uniPorcionResultante;
    }

    public void setUniPorcionResultante(String uniPorcionResultante) {
        this.uniPorcionResultante = uniPorcionResultante;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public List<IngredienteRecetaResponse> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteRecetaResponse> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<String> getPasos() {
        return pasos;
    }

    public void setPasos(List<String> pasos) {
        this.pasos = pasos;
    }

    public void addIngredienteRecetaResponse(IngredienteRecetaResponse newIng) {
        this.ingredientes.add(newIng);
    }
}
