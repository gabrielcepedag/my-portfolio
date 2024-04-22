package com.example.massycake.entities.factory;

import com.example.massycake.entities.constantes.TipoPedidoConst;
import com.example.massycake.entities.Producto;
import com.example.massycake.entities.interfaces.ProductoFactory;

public class CabinaFactory implements ProductoFactory {

    @Override
    public Producto crearPersonalizado(String nombre, String descripcion, int cantidad, Float medida, String unidadMedida, Float inicial, Float descuento) {
        return null;
    }

    @Override
    public Producto crearIngrediente(String nombre, String descripcion, int cantidad, Float medida, String unidadMedida, int dispMin) {
        return null;
    }

    @Override
    public Producto crearCabina(String nombre, String descripcion, int cantidad, Float medida, String unidadMedida, Float descuento, String categoria) {
        Producto p1 = new Producto();
        p1.setNombre(nombre);
        p1.setDescripcion(descripcion);
        p1.setCantidad(cantidad);
        p1.setMedida(medida);
        p1.setUnidadMedida(unidadMedida);
        p1.setDescuento(descuento);
        p1.setCategoria(categoria);
        p1.setTipo(TipoPedidoConst.CABINA);
        return p1;
    }
}
