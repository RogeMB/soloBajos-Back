package com.solobajos.solobajos.repository;

import com.solobajos.solobajos.model.Bajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface BajoRepository extends JpaRepository<Bajo, UUID>, JpaSpecificationExecutor<Bajo> {
}
