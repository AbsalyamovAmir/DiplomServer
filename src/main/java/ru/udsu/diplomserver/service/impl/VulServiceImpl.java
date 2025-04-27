package ru.udsu.diplomserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.udsu.diplomserver.models.Vulnerability;
import ru.udsu.diplomserver.repositories.VulnerabilityRepository;
import ru.udsu.diplomserver.service.VulService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VulServiceImpl implements VulService {

    private final VulnerabilityRepository repository;


    @Transactional
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
//            repository.save(vulnerability);
        });

        try {
            repository.save(vulnerability);
        } catch (Exception exception) {
            System.out.println("123");
        }
    }
}
