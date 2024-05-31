package com.example.massycake.repository;

import com.example.massycake.entities.DetalleFactura;
import com.example.massycake.entities.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetalleFacturaRepo extends JpaRepository<DetalleFactura, Long> {
    List<DetalleFactura> findAll();
    DetalleFactura save(DetalleFactura detalleFactura);
}
