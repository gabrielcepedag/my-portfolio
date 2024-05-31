package com.example.massycake.repository;

import com.example.massycake.entities.Cliente;
import com.example.massycake.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepo extends JpaRepository<Pedido, Long> {
    @Query(value = "SELECT * FROM cliente_no_deleted", nativeQuery = true)
    List<Cliente> myFindALlCliente();
    @Query(value = "SELECT * FROM pedido_no_deleted pnd ORDER BY ABS(DATEDIFF(:now, pnd.fecha_entrega))", nativeQuery = true)
    List<Pedido> myFindAllFromNow(LocalDateTime now);
    @Query(value = "SELECT * FROM pedido_no_deleted pnd ORDER BY ABS(DATEDIFF(:now, pnd.fecha_entrega))", nativeQuery = true)
    List<Pedido> myFindAll();
    @Query(value = "SELECT * " +
            "FROM pedido_no_deleted p " +
            "WHERE p.tipo = :tipoPedido", nativeQuery = true)
    List<Pedido> findAllByTipoPedido(String tipoPedido);

//    List<Pedido> findAllByEliminado(boolean eliminado);
    Optional<Pedido> findById(Long id);
    @Query(value = "SELECT unidad_equivalente " +
            "from conversion_unidades cu1 " +
            "WHERE cu1.tipo = :tipo " +
            "UNION " +
            "SELECT unidad_origen " +
            "from conversion_unidades cu2 " +
            "WHERE cu2.tipo = :tipo " +
            "OR cu2.unidad_origen = 'Unds'", nativeQuery = true)
    List<String> getUnidadesByTipo(String tipo);

    @Query(value = "SELECT cu.factor " +
            "FROM conversion_unidades cu " +
            "WHERE cu.unidad_origen = :origen " +
            "AND cu.unidad_equivalente = :resultante", nativeQuery = true)
    Float findFactor(String origen, String resultante);

    @Query(value = "SELECT * from pedido_no_deleted pnd \n" +
            "WHERE pnd.fecha_entrega BETWEEN :now AND DATE_ADD(:now, INTERVAL :days DAY) \n" +
            "ORDER BY pnd.fecha_entrega ASC", nativeQuery = true)
    List<Pedido> findAllPendientesByDays(LocalDateTime now, int days);

    @Query(value = "SELECT COUNT(dpr.receta_id) as 'Cantidad' \n" +
            "FROM detalle_pedido_receta dpr \n" +
            "INNER JOIN detalle_pedido dp \n" +
            "ON dpr.detalle_pedido_id = dp.detalle_pedido_id  \n" +
            "WHERE dp.pedido_id = :pedidoId", nativeQuery = true)
    int findCantReceta(long pedidoId);


    // Puedes agregar consultas personalizadas adicionales según tus necesidades
}
