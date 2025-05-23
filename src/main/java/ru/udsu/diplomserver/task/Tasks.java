package ru.udsu.diplomserver.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.udsu.diplomserver.service.fillingdb.ImportService;

@Component
@Slf4j
@RequiredArgsConstructor
public class Tasks {

    private final ImportService importService;

    @Scheduled(cron = "${fstec.task.cron}")
    public void downloadXmlFromFstecTask() {
        try {
            log.info("Запуск задачи обновления базы данных уязвимостей");
            importService.getAndSaveVulnerabilitiesFromFstec();
            log.info("Задача по сохранению или обновлению базы завершилась успешно");
        } catch (Exception e) {
            log.error("Произошла ошибка при парсинге xml файла message = {}", e.getMessage());
        }
    }
}
