package com.example.practica2.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByUsername(String username);
    Optional<User> findByUsername(String username);

    @Query("SELECT DISTINCT u FROM User u JOIN u.roles r WHERE r.id = 1")
    List<User> getUsersByAdmin();
}
