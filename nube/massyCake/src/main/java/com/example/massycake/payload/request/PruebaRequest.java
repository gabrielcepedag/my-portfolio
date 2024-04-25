package com.example.massycake.payload.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class PruebaRequest {
    @NotNull(message = "ID no puede ser NULL")
    private Long id;
    @Min(value = 1, message = "El valor de la cantidad debe ser mayor que cero")
    private int cantidad;
    @NotNull(message = "Nota no puede ser NULL")
    private String nota;
    @NotNull(message = "Lista no puede ser NULL")
    private List<LoginRequest> lista;

    public Long getId() {
        return id;
    }
    public List<LoginRequest> getLista() {
        return lista;
    }

    public void setLista(List<LoginRequest> lista) {
        this.lista = lista;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
