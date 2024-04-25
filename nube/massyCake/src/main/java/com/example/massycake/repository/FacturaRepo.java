package com.example.massycake.repository;

import com.example.massycake.entities.Cliente;
import com.example.massycake.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface FacturaRepo extends JpaRepository<Factura, Long> {
    Optional<Factura> findByFacturaId(String id);

    @Query(value = "SELECT SUM(f.total) from factura f " +
            "WHERE DATEDIFF( f.fecha, :fecha ) = 0", nativeQuery = true)
    Float getIngresosFacturasHoy(String fecha);
    @Query(value = "SELECT SUM(f.total) FROM factura f " +
            "WHERE MONTH(f.fecha) = MONTH(:now) AND YEAR(f.fecha) = YEAR(:now)", nativeQuery = true)
    Float getIngresosByMes(String now);

    @Query(value = "SELECT SUM(f.total) FROM factura f " +
            "WHERE YEAR(f.fecha) = YEAR(:now)", nativeQuery = true)
    Float getIngresosByAno(String now);
    @Query(value = "SELECT COUNT(f.factura_id) from factura f \n" +
            "WHERE DATEDIFF( f.fecha, :fecha ) = 0", nativeQuery = true)
    Integer getCantVentas(String fecha);
//    List<Cliente> findAllByEliminado(boolean eliminado);

    @Query(value = "SELECT COUNT(*) FROM detalle_factura df, producto p " +
            "WHERE df.id = p.producto_id AND p.tipo = :tipo", nativeQuery = true)
    Integer countByTipo(String tipo);

}
