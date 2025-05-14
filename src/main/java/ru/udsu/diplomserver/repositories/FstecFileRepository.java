package ru.udsu.diplomserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.udsu.diplomserver.models.FstecFile;

public interface FstecFileRepository extends JpaRepository<FstecFile, Long> {

    @Query("select f from FstecFile f  where f.id = 1")
    FstecFile getFile();

}
