package com.example.massycake.entities.factory;

import com.example.massycake.entities.Producto;
import com.example.massycake.entities.constantes.TipoPedidoConst;
import com.example.massycake.entities.interfaces.ProductoFactory;

public class PersonalizadoFactory implements ProductoFactory {
    @Override
    public Producto crearPersonalizado(String nombre, String descripcion, int cantidad, Float medida, String unidadMedida, Float inicial, Float descuento) {
//        Producto p1 = new Producto(nombre, descripcion, cantidad, medida, unidadMedida, 0 ,TipoPedidoConst.PERSONALIZADO);
        Producto p1 = new Producto();
        p1.setNombre(nombre);
        p1.setDescripcion(descripcion);
        p1.setCantidad(cantidad);
        p1.setMedida(medida);
        p1.setUnidadMedida(unidadMedida);
        p1.setInicial(inicial);
        p1.setDescuento(descuento);
        p1.setTipo(TipoPedidoConst.PERSONALIZADO);
        return p1;
    }

    @Override
    public Producto crearIngrediente(String nombre, String descripcion, int cantidad, Float medida, String unidadMedida, int dispMin) {
        return null;
    }

    @Override
    public Producto crearCabina(String nombre, String descripcion, int cantidad, Float medida, String unidadMedida, Float descuento, String categoria) {
        return null;
    }
}
