package ru.udsu.diplomserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.udsu.diplomserver.models.Software;
import ru.udsu.diplomserver.models.Vendor;

import java.util.List;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Long> {

    List<Software> findByVendorAndNameContainingIgnoreCase(Vendor vendor, String namePart);

    @Query(value = """
        SELECT s.* 
        FROM software s
        WHERE s.id IN (
            SELECT DISTINCT res.id 
            FROM (
                SELECT 
                    *,
                    similarity(name, :searchItem) AS similarity_score
                FROM software
                WHERE name % :searchItem
                ORDER BY similarity_score DESC
            ) AS res
            WHERE res.similarity_score >= :score
        )
        """, nativeQuery = true)
    List<Software> findBySimilarityScore(String searchItem, double score);

}
