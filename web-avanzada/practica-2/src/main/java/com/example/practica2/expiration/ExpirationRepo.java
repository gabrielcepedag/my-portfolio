package com.example.practica2.expiration;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpirationRepo extends JpaRepository<Expiration, Long> {
}
