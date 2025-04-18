package ru.udsu.diplomserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.udsu.diplomserver.models.Software;
import ru.udsu.diplomserver.models.Vendor;

import java.util.Optional;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Long> {

    Optional<Software> findByNameAndVersionAndVendor(String name, String version, Vendor vendor);

    @Query("SELECT s FROM Software s JOIN FETCH s.types WHERE s.name = :name AND s.version = :version")
    Optional<Software> findByNameAndVersionWithTypes(String name, String version);

    boolean existsByNameAndVersionAndVendor(String name, String version, Vendor vendor);
}
