package com.example.massycake.entities.interfaces;

import com.example.massycake.entities.Producto;

public interface ProductoFactory {
    Producto crearPersonalizado(String nombre, String descripcion, int cantidad, Float medida, String unidadMedida, Float inicial, Float descuento);

    public Producto crearIngrediente(String nombre, String descripcion, int cantidad, Float medida, String unidadMedida, int dispMin);

    public Producto crearCabina(String nombre, String descripcion, int cantidad, Float medida, String unidadMedida, Float descuento, String categoria);
}
