package com.example.massycake.repository;

import com.example.massycake.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ClienteRepo extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCedula(String cedula);
//    List<Cliente> findAllByEliminado(boolean eliminado);

    @Query(value = "SELECT * FROM cliente_no_deleted", nativeQuery = true)
    List<Cliente> myFindALl();
}
