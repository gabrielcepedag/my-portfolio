package com.example.massycake.entities;

import com.example.massycake.payload.response.IngredienteRecetaResponse;
import com.example.massycake.payload.response.IngredienteResponse;
import com.example.massycake.payload.response.RecetaResponse;
import com.example.massycake.utils.StringListConverter;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private int minutosPreparacion;
    private int porcionResultante;
    @Column(updatable = false)
    private String uniPorcionResultante;
    @Column(columnDefinition = "boolean default false")
    private boolean eliminado;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ingrediente_receta")
    private List<IngredienteReceta> ingredientes;

    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> pasos;

    @ManyToMany
    List<DetallePedido> detallePedidoList;

    public Receta(String nombre, String descripcion, int minutosPreparacion, int porcionResultante, String uniPorcionResultante) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.minutosPreparacion = minutosPreparacion;
        this.porcionResultante = porcionResultante;
        this.uniPorcionResultante = uniPorcionResultante;
        this.ingredientes = new ArrayList<>();
        this.pasos = new ArrayList<>();
    }

    public List<DetallePedido> getDetallePedidoList() {
        return detallePedidoList;
    }

    public void setDetallePedidoList(List<DetallePedido> detallePedidoList) {
        this.detallePedidoList = detallePedidoList;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
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

    public Receta() {
    }

    public void setId(Long id){
        this.id = id;
    }
    public Long getId() {
        return id;
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

    public List<IngredienteReceta> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteReceta> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void addIngrediente(IngredienteReceta ingrediente){
        this.ingredientes.add(ingrediente);
    }
    public List<String> getPasos() {
        return pasos;
    }

    public void setPasos(List<String> pasos) {
        this.pasos = pasos;
    }

    public RecetaResponse getRecetaResponse(){
        RecetaResponse receta = new RecetaResponse();
        receta.setId(this.id);
        receta.setNombre(this.nombre);
        receta.setDescripcion(this.descripcion);
        receta.setEliminado(this.eliminado);
        receta.setPasos(this.pasos);
        receta.setMinutosPreparacion(this.minutosPreparacion);
        receta.setPorcionResultante(this.porcionResultante);
        receta.setUniPorcionResultante(this.uniPorcionResultante);

        receta.setIngredientes(new ArrayList<>());
        for (IngredienteReceta ing : this.ingredientes) {
            IngredienteRecetaResponse newIng = ing.getIngredienteRecetaResponse();
            receta.addIngredienteRecetaResponse(newIng);
        }

        return receta;
    }
}
