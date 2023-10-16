package dev.jotxee.repository;


import dev.jotxee.repository.entity.IPEntity;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface IPRepository extends JpaRepository<IPEntity, Long> {
    @Query(value = "SELECT * FROM ip_entity ORDER BY instant DESC LIMIT 1", nativeQuery = true)
    Optional<IPEntity> findLastRecord();
}
