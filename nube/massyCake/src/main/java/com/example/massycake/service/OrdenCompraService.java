package com.example.massycake.service;

import com.example.massycake.entities.OrdenCompra;
import com.example.massycake.repository.OrdenCompraRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenCompraService {
    private final OrdenCompraRepo ordenCompraRepo;

    public OrdenCompraService(OrdenCompraRepo ordenCompraRepo) {
        this.ordenCompraRepo = ordenCompraRepo;
    }

    public List<OrdenCompra> getAllOrdenesCompra() {
        return ordenCompraRepo.findAllNotDeleted();
    }

    public OrdenCompra createOrdenCompra(OrdenCompra ordenCompra) {
        return ordenCompraRepo.save(ordenCompra);
    }

    public OrdenCompra deleteOrdenCompra(Long idOrdenCompra) {
        OrdenCompra ordenCompra = (OrdenCompra) ordenCompraRepo.findById(idOrdenCompra).orElse(null);
        if (ordenCompra == null) {
            return null;
        }
        ordenCompra.setEliminada(true);
        ordenCompraRepo.save(ordenCompra);
        return ordenCompra;
    }

    public OrdenCompra updateOrdenCompra(OrdenCompra ordenCompra) {
        return ordenCompraRepo.save(ordenCompra);
    }

    public OrdenCompra getOrdenCompraById(Long idOrdenCompra) {
        return (OrdenCompra) ordenCompraRepo.findById(idOrdenCompra).orElse(null);
    }
}