package ru.udsu.diplomserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.udsu.diplomserver.models.Source;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {
}
