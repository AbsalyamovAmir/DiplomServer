package ru.udsu.diplomserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.udsu.diplomserver.models.SoftwareType;

@Repository
public interface SoftwareTypeRepository extends JpaRepository<SoftwareType, Long> {
}
