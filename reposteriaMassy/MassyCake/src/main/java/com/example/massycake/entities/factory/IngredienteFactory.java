package com.example.massycake.entities.factory;

import com.example.massycake.entities.constantes.TipoPedidoConst;
import com.example.massycake.entities.Producto;
import com.example.massycake.entities.interfaces.ProductoFactory;

public class IngredienteFactory implements ProductoFactory {
    @Override
    public Producto crearPersonalizado(String nombre, String descripcion, int cantidad, Float medida, String unidadMedida, Float inicial, Float descuento) {
        return null;
    }

    @Override
    public Producto crearIngrediente(String nombre, String descripcion, int cantidad, Float medida, String unidadMedida, int dispMin) {
        Producto ingrediente = new Producto();
        ingrediente.setNombre(nombre);
        ingrediente.setDescripcion(descripcion);
        ingrediente.setCantidad(cantidad);
        ingrediente.setMedida(medida);
        ingrediente.setUnidadMedida(unidadMedida);
        ingrediente.setDispMin(dispMin);
        ingrediente.setTipo(TipoPedidoConst.INGREDIENTE);
        return ingrediente;
//        return new Producto(nombre, descripcion, cantidad, medida, unidadMedida, dispMin, TipoPedidoConst.INGREDIENTE);
    }

    @Override
    public Producto crearCabina(String nombre, String descripcion, int cantidad, Float medida, String unidadMedida, Float descuento, String categoria) {
        return null;
    }
}
