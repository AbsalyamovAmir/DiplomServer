package ru.udsu.diplomserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.udsu.diplomserver.models.OperatingSystem;

import java.util.Optional;

@Repository
public interface OperatingSystemRepository extends JpaRepository<OperatingSystem, Long> {
    @Query("SELECT os FROM OperatingSystem os WHERE os.name = :name AND os.version = :version")
    Optional<OperatingSystem> findByNameAndVersion(String name, String version);

    boolean existsByNameAndVersion(String name, String version);
}
