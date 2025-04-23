package ru.udsu.diplomserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.udsu.diplomserver.models.CWE;

import java.util.Optional;

@Repository
public interface CweRepository extends JpaRepository<CWE, Long> {
    Optional<CWE> findByIdentifier(String identifier);
    boolean existsByIdentifier(String identifier);
}
