package com.example.massycake.repository;

import com.example.massycake.entities.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenCompraRepo extends JpaRepository<OrdenCompra, Long> {

    List<OrdenCompra> findAll();

    @Query(value = "SELECT * FROM orden_compra oc where oc.eliminada = 0", nativeQuery = true)
    List<OrdenCompra> findAllNotDeleted();

    OrdenCompra save(OrdenCompra ordenCompra);

    Optional<OrdenCompra> findById(Long idOrdenCompra);
}
