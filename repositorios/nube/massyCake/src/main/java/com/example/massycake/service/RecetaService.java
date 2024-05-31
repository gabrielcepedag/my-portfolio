package com.example.massycake.service;

import com.example.massycake.entities.IngredienteReceta;
import com.example.massycake.entities.Pedido;
import com.example.massycake.entities.Producto;
import com.example.massycake.entities.Receta;
import com.example.massycake.payload.response.RecetaResponse;
import com.example.massycake.repository.RecetaRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {
    private final RecetaRepo recetaRepo;
    private final ProductoService productoService;

    public RecetaService(RecetaRepo recetaRepo, ProductoService productoService) {
        this.recetaRepo = recetaRepo;
        this.productoService = productoService;
    }

    public List<Receta> getAllRecetas() {
        return recetaRepo.myFindAll();
    }

    public Receta createReceta(Receta receta) {
        return recetaRepo.save(receta);
    }

    public Receta deleteReceta(Long idReceta) {
        Receta receta = recetaRepo.findById(idReceta).orElse(null);
        if (receta == null) {
            return null;
        }
        receta.setEliminado(true);
        recetaRepo.save(receta);
        return receta;
    }

    public Receta updateReceta(Receta receta) {
        Receta r1 = recetaRepo.findById(receta.getId()).orElse(null);
        if (r1 != null){
            receta.setUniPorcionResultante(r1.getUniPorcionResultante());
        }
        return recetaRepo.save(receta);
    }

    public Receta getRecetaById(Long idReceta) {
        return recetaRepo.findById(idReceta).orElse(null);
    }

    public int getCantRecetasVacias() {
        return recetaRepo.findCantRecetasVacias();
    }

    public List<Receta> getRecetasVacias() {
        List<Receta> lista = recetaRepo.myFindAll();
        Iterator<Receta> iterator = lista.iterator();

        while (iterator.hasNext()) {
            Receta receta = iterator.next();
            if (!receta.getIngredientes().isEmpty()) {
                iterator.remove(); // Use iterator to safely remove elements
            }
        }

        return lista;
    }

    public void addIngrediente(long idReceta, IngredienteReceta ingrediente){
        Receta receta = getRecetaById(idReceta);
        receta.addIngrediente(ingrediente);

        updateReceta(receta);
    }
    public void deleteIngrediente(long idReceta, long idIngrediente) {
        Receta receta = getRecetaById(idReceta);
        Producto producto = productoService.getProductoById(idIngrediente);

        Iterator<IngredienteReceta> iterator = receta.getIngredientes().iterator();
        while (iterator.hasNext()) {
            IngredienteReceta ingrediente = iterator.next();
            if (ingrediente.getIngrediente().getProductoId().equals(producto.getProductoId())) {
                iterator.remove();
            }
        }
        updateReceta(receta);
    }

    public List<RecetaResponse> getRecetasEquivalentesByPedido(Pedido pedido) {
        List<RecetaResponse> recetas = new ArrayList<>();
        List<Long> recetasId = recetaRepo.findRecetasEquivalentesByPedido(pedido.getPedidoId());
        for (Long id: recetasId) {
            RecetaResponse receta = recetaRepo.findById(id).orElse(null).getRecetaResponse();
            if (receta != null) {
                recetas.add(receta);
            }
        }
        return recetas;
    }

    public List<Receta> getRecetasNotUnds() {
        List<Receta> recetas = recetaRepo.getRecetasNotUnds();

        return recetas;
    }
}
