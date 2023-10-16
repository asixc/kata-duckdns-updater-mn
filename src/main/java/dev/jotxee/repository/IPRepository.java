package dev.jotxee.repository;


import dev.jotxee.repository.entity.IPEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface IPRepository extends JpaRepository<IPEntity, Long> {
    // Optional<IPEntity> findFirstByOrderByInstantDesc();
   // List<IPEntity> findAll();
/*
    # Primera opción poco eficiente
    @Query("SELECT i FROM IPEntity i WHERE i.instant = (SELECT MAX(i2.instant) FROM IPEntity i2)")
    Optional<IPEntity> findLastRecord();

    # Segunda algo mejor:
    @Query("SELECT i FROM IPEntity i ORDER BY i.instant DESC")
    Optional<IPEntity> findLastRecord();
*/
// # Tercera:
    @Query(value = "SELECT * FROM ip_entity ORDER BY instant DESC LIMIT 1", nativeQuery = true)
    Optional<IPEntity> findLastRecord();
}
