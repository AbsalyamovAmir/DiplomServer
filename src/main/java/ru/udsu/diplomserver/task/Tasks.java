package ru.udsu.diplomserver.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.udsu.diplomserver.service.VulnerabilityImportService;

@Component
@Slf4j
@RequiredArgsConstructor
public class Tasks {

    private final VulnerabilityImportService vulnerabilityImportService;

    @Scheduled(cron = "${fstec.task.cron}")
    public void downloadXmlFromFstecTask() {
        try {
            vulnerabilityImportService.getAndSaveVulnerabilitiesFromFstec();
        } catch (Exception e) {
            log.error("downloadXmlFromFstecTask ERROR {}", e.getMessage());
        }
    }
}
