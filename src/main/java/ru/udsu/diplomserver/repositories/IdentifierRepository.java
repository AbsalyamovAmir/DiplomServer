package ru.udsu.diplomserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.udsu.diplomserver.models.Identifier;

import java.util.Optional;

@Repository
public interface IdentifierRepository extends JpaRepository<Identifier, Long> {
    @Query("SELECT i FROM Identifier i WHERE i.type = :type AND i.value = :value")
    Optional<Identifier> findByTypeAndValue(String type, String value);

    boolean existsByTypeAndValue(String type, String value);
}
