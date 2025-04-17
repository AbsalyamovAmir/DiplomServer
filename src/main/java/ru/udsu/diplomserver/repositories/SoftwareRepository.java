package ru.udsu.diplomserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.udsu.diplomserver.models.Software;

import java.util.Optional;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Long> {
    @Query("SELECT s FROM Software s WHERE s.name = :name AND s.version = :version")
    Optional<Software> findByNameAndVersion(String name, String version);

    @Query("SELECT s FROM Software s JOIN FETCH s.types WHERE s.name = :name AND s.version = :version")
    Optional<Software> findByNameAndVersionWithTypes(String name, String version);

    boolean existsByNameAndVersion(String name, String version);
}
