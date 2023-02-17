package com.solobajos.solobajos.repository;

import com.solobajos.solobajos.model.Bass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface BassRepository extends JpaRepository<Bass, UUID>, JpaSpecificationExecutor<Bass> {
}
