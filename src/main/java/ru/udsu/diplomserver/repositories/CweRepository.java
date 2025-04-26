package ru.udsu.diplomserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.udsu.diplomserver.models.Cwe;

import java.util.Optional;

@Repository
public interface CweRepository extends JpaRepository<Cwe, Long> {
    Optional<Cwe> findByIdentifier(String identifier);
    boolean existsByIdentifier(String identifier);
}
