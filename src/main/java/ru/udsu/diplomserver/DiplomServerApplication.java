package ru.udsu.diplomserver;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ru.udsu.diplomserver.dto.FstecVulnerabilityListDto;
import ru.udsu.diplomserver.services.FstecXmlParserService;

@SpringBootApplication
public class DiplomServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomServerApplication.class, args);
        // Пример использования (для тестирования)
        FstecXmlParserService parser = new FstecXmlParserService();
        FstecVulnerabilityListDto result = parser.parseVulnerabilitiesFromXml("E:\\export.xml");

        // Вывод информации для проверки
        if (result != null && result.getVulnerabilities() != null) {
            System.out.println("Найдено уязвимостей: " + result.getVulnerabilities().size());
            result.getVulnerabilities().stream()
                    .limit(5) // выводим только первые 5 для примера
                    .forEach(vuln -> System.out.println(
                            "ID: " + vuln.getIdentifier() +
                                    ", Название: " + vuln.getName() +
                                    ", Уровень опасности: " + vuln.getSeverity()));
        }
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public JAXBContext jaxbContext() throws JAXBException {
        return JAXBContext.newInstance(FstecVulnerabilityListDto.class);
    }
}
