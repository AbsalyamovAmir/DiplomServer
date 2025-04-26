package ru.udsu.diplomserver.service;

import ru.udsu.diplomserver.models.Vulnerability;

public interface VulService {

    void checkAndSaveVul(Vulnerability vulnerability);

}
