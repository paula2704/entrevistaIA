package com.empresa.entrevistaIA.repository;

import com.empresa.entrevistaIA.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SesionRepository extends JpaRepository<Sesion, Long> {
}