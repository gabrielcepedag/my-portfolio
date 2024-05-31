package com.example.massycake.repository;

import com.example.massycake.entities.Cliente;
import com.example.massycake.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepo extends JpaRepository<Empleado, Long> {
    //HERE WE DEfINE CUSTOM METHODS... ALSO NOW WE CAN USE JPA's METHODS WITHOUT IMPLEMENTING IT
    @Query(value = "SELECT * FROM empleado_no_deleted", nativeQuery = true)
    List<Empleado> myFindALl();
    Optional<Empleado> findByCedula(String cedula);
    Optional<Empleado> findByUsuario(String usuario);
    @Query(value = "SELECT count(e.empleado_id) from empleado_no_deleted e", nativeQuery = true)
    int countEmpleado();
    @Query(value = "SELECT count(e.empleado_id) from empleado_no_deleted e WHERE e.rol LIKE 'REPOSTERO'", nativeQuery = true)
    int getCantReposteros();
    @Query(value = "SELECT count(e.empleado_id) from empleado_no_deleted e WHERE e.rol LIKE 'CAJERO'", nativeQuery = true)
    int getCantCajeros();
}
