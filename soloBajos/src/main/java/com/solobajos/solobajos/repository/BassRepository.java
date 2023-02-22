package com.solobajos.solobajos.repository;

import com.solobajos.solobajos.model.Bass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BassRepository extends JpaRepository<Bass, UUID>, JpaSpecificationExecutor<Bass> {
    @Query("SELECT b FROM Bass b JOIN b.userList ul WHERE ul.id = :userId")
    List<Bass> bassListFavs(@Param("userId") UUID userId);

}