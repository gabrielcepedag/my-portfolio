package com.example.massycake.repository;

import com.example.massycake.entities.Producto;
import com.example.massycake.entities.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecetaRepo extends JpaRepository<Receta, Long> {
//    List<Receta> findAllByEliminado(boolean eliminado);
    @Query(value = "SELECT * FROM receta_no_deleted", nativeQuery = true)
    List<Receta> myFindAll();
    @Query(value = "SELECT * FROM receta_no_deleted r WHERE r.id = :id", nativeQuery = true)
    Optional<Receta> findById(Long id);

    @Query(value = "SELECT COUNT(r.id) recetasSinIngredientes " +
            "FROM receta_no_deleted r " +
            "LEFT JOIN ingrediente_receta ir " +
            "ON r.id = ir.receta_id " +
            "WHERE ir.receta_id IS NULL", nativeQuery = true)
    int findCantRecetasVacias();

    @Query(value = "SELECT DISTINCT(rnd.id) " +
            "FROM detalle_pedido dp " +
            "INNER JOIN receta_no_deleted rnd " +
            "ON dp.unidad = rnd.uni_porcion_resultante " +
            "WHERE dp.pedido_id = :pedidoId", nativeQuery = true)
    List<Long> findRecetasEquivalentesByPedido(long pedidoId);
    @Query(value = "SELECT * " +
            "FROM receta_no_deleted rnd " +
            "WHERE rnd.uni_porcion_resultante NOT LIKE 'Unds' ",  nativeQuery = true)
    List<Receta> getRecetasNotUnds();

}
