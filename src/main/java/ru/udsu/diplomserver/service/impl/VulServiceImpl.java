package ru.udsu.diplomserver.service.impl;

import org.springframework.stereotype.Service;
import ru.udsu.diplomserver.models.Vulnerability;
import ru.udsu.diplomserver.repositories.VulnerabilityRepository;
import ru.udsu.diplomserver.service.VulService;

import java.util.Optional;

@Service
public class VulServiceImpl implements VulService {

    VulnerabilityRepository repository;

    @Override
    public void checkAndSaveVul(Vulnerability vulnerability) {

        if (vulnerability.hash() == null) {
            return;
        }

        if (repository.existsByHash(vulnerability.hash())) {
            return;
        }

        Optional<Vulnerability> optVul = repository.findByIdentifier(vulnerability.identifier());

        optVul.ifPresent(vul -> {
            repository.delete(vul);
            repository.save(vulnerability);
        });
    }
}
