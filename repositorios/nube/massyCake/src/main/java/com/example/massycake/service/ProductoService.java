package com.example.massycake.service;

import com.example.massycake.entities.Pedido;
import com.example.massycake.entities.Producto;
import com.example.massycake.entities.Receta;
import com.example.massycake.entities.constantes.TipoPedidoConst;
import com.example.massycake.entities.factory.IngredienteFactory;
import com.example.massycake.repository.ProductoRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductoService {
    private ModelMapper modelMapper = new ModelMapper();
    private final ProductoRepo productoRepo;

    public ProductoService(ProductoRepo productoRepo) {
        this.productoRepo = productoRepo;
    }

    public List<Producto> getAllIngredientes() {
        List<Producto> ingredientes = productoRepo.findByTipoAndEliminado(TipoPedidoConst.INGREDIENTE, false);

        return ingredientes;
    }

    public Producto createIngrediente(Producto producto){
        Producto ingrediente = new IngredienteFactory().crearIngrediente(producto.getNombre(),producto.getDescripcion(),
                producto.getCantidad(), producto.getMedida(), producto.getUnidadMedida(), producto.getDispMin());

        return productoRepo.save(ingrediente);
    }

    public Producto editIngrediente(Producto producto) {
        Producto local = productoRepo.findById(producto.getProductoId()).orElse(null);
        if (local != null && local.getTipo().equalsIgnoreCase(TipoPedidoConst.INGREDIENTE)){
            producto.setUnidadMedida(local.getUnidadMedida());
        }
        return productoRepo.save(producto);
    }

    public Producto deleteIngrediente(Long idIngrediente) {
        Producto prod = productoRepo.findById(idIngrediente).orElse(null);

        if (prod == null || !prod.getTipo().equalsIgnoreCase("Ingrediente")){
            return null;
        }
        prod.setEliminado(true);
        productoRepo.save(prod);
        return prod;
    }

    public Producto getProductoById(Long idProducto) {
        return productoRepo.findById(idProducto).orElse(null);
    }

    public List<Producto> findAllIngredientesNotInReceta(long idReceta) {
        return productoRepo.findAllNotInReceta(idReceta);
    }

    public List<String> getAllUnidades() {
        List<String> unidades = productoRepo.getAllUnidades();
        return unidades;
    }

    public List<String> getUnidadesEquivalentes(String unidadMedida) {
        List<String> unidadesEquivalentes = new ArrayList<>();
        unidadesEquivalentes =productoRepo.getUnidadesEquivalentes(unidadMedida);
        if (unidadesEquivalentes.size() > 0){
            unidadesEquivalentes.add(unidadMedida);
            Collections.swap(unidadesEquivalentes, 0, unidadesEquivalentes.indexOf(unidadMedida));
            return unidadesEquivalentes;
        }
        return new ArrayList<>();
    }

    public List<Producto> getAllCabina() {
        List<Producto> cabina = productoRepo.getAllCabina();
        return cabina;
    }

    public List<Producto> getAllCabinaDisp() {
        List<Producto> cabinaDisp = productoRepo.getAllCabinaDisponibles();
        return cabinaDisp;
    }

    public List<Producto> getAllPersonalizados() {
        List<Producto> personalizados = productoRepo.getAllPersonalizados();
        return personalizados;
    }

    public List<Producto> getAllPersonalizadosDisp() {
        List<Producto> personalizados = productoRepo.getAllPersonalizadosDisp();
        return personalizados;
    }

    public List<Producto> buscarProductosCategoria(String categoria) {
        List<Producto> productos = new ArrayList<>();

        if (categoria.equalsIgnoreCase("todos")){
            productos = productoRepo.findAllProductosDisp();
        }else{
            productos = productoRepo.findAllProductosDisp(categoria);
        }
        return productos;
    }

    public void guardarProducto(Producto producto) {
        productoRepo.save(producto);
    }

    public List<Producto> getAllProductosNotIngrediente() {
        return productoRepo.findAllProductoNotTipo(TipoPedidoConst.INGREDIENTE);
    }

    public List<Producto> getAllProductos() {
        return productoRepo.findAll();
    }

    public void actualizarProducto(Producto producto) {
        productoRepo.save(producto);
    }
}
