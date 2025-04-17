package ru.udsu.diplomserver.services;

import org.springframework.http.HttpInputMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.zip.ZipInputStream;

@Service
public class DownloadService {
    public ZipInputStream downloadZipFile() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://bdu.fstec.ru/files/documents/vulxml.zip";
        //keytool -import -trustcacerts -keystore "C:\Program Files\Java\jdk-21\lib\security\cacerts" -storepass changeit -noprompt -alias bdufstec -file "C:\Users\Absal\Downloads\_.fstec.ru"
        return (ZipInputStream) restTemplate.execute(URI.create(url), org.springframework.http.HttpMethod.GET, null, HttpInputMessage::getBody);
    }
}