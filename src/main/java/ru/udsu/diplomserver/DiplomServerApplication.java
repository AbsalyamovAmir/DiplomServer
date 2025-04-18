package ru.udsu.diplomserver;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ru.udsu.diplomserver.dto.FstecVulnerabilityListDto;

@SpringBootApplication
public class DiplomServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomServerApplication.class, args);
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
