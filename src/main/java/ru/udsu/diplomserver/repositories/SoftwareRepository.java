package ru.udsu.diplomserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.udsu.diplomserver.models.Software;
import ru.udsu.diplomserver.models.Vendor;

import java.util.List;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Long> {

    List<Software> findByVendorAndNameContainingIgnoreCase(Vendor vendor, String namePart);
}
