package com.example.massycake.repository;

import com.example.massycake.entities.Producto;
import com.example.massycake.entities.constantes.TipoPedidoConst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static com.example.massycake.entities.constantes.TipoPedidoConst.INGREDIENTE;

public interface IngredienteRepo extends JpaRepository<Producto, Long> {

}
