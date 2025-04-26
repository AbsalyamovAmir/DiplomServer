package ru.udsu.diplomserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.udsu.diplomserver.models.VulnerableSoftware;
import ru.udsu.diplomserver.models.Vendor;

import java.util.Optional;

@Repository
public interface SoftwareRepository extends JpaRepository<VulnerableSoftware, Long> {

    Optional<VulnerableSoftware> findByNameAndVersionAndVendor(String name, String version, Vendor vendor);

    @Query("SELECT s FROM VulnerableSoftware s JOIN FETCH s.types WHERE s.name = :name AND s.version = :version")
    Optional<VulnerableSoftware> findByNameAndVersionWithTypes(String name, String version);

    boolean existsByNameAndVersionAndVendor(String name, String version, Vendor vendor);
}
