package com.microservices.analyzer.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TargetRepository extends JpaRepository<TargetEntity, UUID> {
    Optional<TargetEntity> findByUuid(UUID uuid);
}
