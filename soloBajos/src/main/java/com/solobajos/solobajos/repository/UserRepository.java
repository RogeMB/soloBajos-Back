package com.solobajos.solobajos.repository;

import com.solobajos.solobajos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findFirstByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Query(value = "SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Bass b JOIN b.userList u WHERE b.id = :bassId AND u.id = :userId")
    boolean findFirstFav(@Param("userId") UUID userId, @Param("bassId") UUID bassId);

    /* @Query("""
            SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
            FROM Bass b JOIN FETCH b.userList u
            WHERE b.id = :bassId AND u.id = :userId
            """)*/
}