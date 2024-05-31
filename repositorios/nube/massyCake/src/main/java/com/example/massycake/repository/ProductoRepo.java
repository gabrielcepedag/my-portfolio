package com.example.massycake.repository;

import com.example.massycake.entities.Producto;
import com.example.massycake.entities.IngredienteReceta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepo extends JpaRepository<Producto, Long> {

    List<Producto> findByTipoAndEliminado(String tipo, boolean eliminado);
    List<Producto> findAllByTipo(String tipo);

    @Query(value = "SELECT DISTINCT(p.producto_id), p.nombre, p.cantidad, p.descripcion, p.descuento, p.disp_min, p.inicial, p.medida, p.unidad_medida, p.eliminado, p.tipo, p.categoria, p.foto, p.precio " +
            "FROM producto_no_deleted p " +
            "LEFT JOIN ingrediente_receta ir " +
            "ON p.producto_id = ir.producto_id " +
            "AND p.producto_id NOT IN (" +
            "SELECT ir2.producto_id FROM ingrediente_receta ir2 " +
            "WHERE ir2.receta_id = ?1) ", nativeQuery = true)
    List<Producto> findAllNotInReceta(long idReceta);

    @Query(value = "SELECT DISTINCT(cu.unidad_origen)" +
            " FROM conversion_unidades cu ", nativeQuery = true)
    List<String> getAllUnidades();

    @Query(value = "SELECT cu.unidad_equivalente \n" +
            "FROM conversion_unidades cu \n" +
            "WHERE unidad_origen = :unidadMedida", nativeQuery = true)
    List<String> getUnidadesEquivalentes(String unidadMedida);

    @Query(value = "SELECT * FROM producto WHERE producto.tipo LIKE 'cabina';", nativeQuery = true)
    List<Producto> getAllCabina();

    @Query(value = "SELECT * FROM producto WHERE producto.tipo LIKE 'cabina' AND producto.cantidad > 0;", nativeQuery = true)
    List<Producto> getAllCabinaDisponibles();

    @Query(value = "SELECT * FROM producto WHERE producto.tipo LIKE 'personalizado';", nativeQuery = true)
    List<Producto> getAllPersonalizados();

    @Query(value = "SELECT * FROM producto WHERE producto.tipo LIKE 'personalizado' AND producto.cantidad > 0;", nativeQuery = true)
    List<Producto> getAllPersonalizadosDisp();

    @Query(value = "SELECT * FROM producto WHERE producto.tipo NOT LIKE 'ingrediente' AND producto.cantidad > 0;", nativeQuery = true)
    List<Producto> findAllProductosDisp();

    @Query(value = "SELECT * FROM producto WHERE producto.tipo LIKE 'cabina' AND producto.categoria LIKE :categoria AND producto.cantidad > 0;", nativeQuery = true)
    List<Producto> findAllProductosDisp(String categoria);

    @Query(value = "SELECT * FROM producto_no_deleted p \n " +
            "where p.tipo NOT LIKE :tipo", nativeQuery = true)
    List<Producto> findAllProductoNotTipo(String tipo);
}
