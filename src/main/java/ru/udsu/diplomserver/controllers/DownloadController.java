package ru.udsu.diplomserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.udsu.diplomserver.services.ZipDownloaderService;

@RestController
public class DownloadController {

    @Autowired
    private ZipDownloaderService zipDownloaderService;

//    @GetMapping("/download-xml")
//    public ResponseEntity<FileSystemResource> downloadXml() throws IOException {
//        File xmlFile = zipDownloaderService.downloadAndExtractXml(
//                "https://bdu.fstec.ru/files/documents/vulxml.zip");
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=\"" + xmlFile.getName() + "\"")
//                .contentType(MediaType.APPLICATION_XML)
//                .contentLength(xmlFile.length())
//                .body(new FileSystemResource(xmlFile));
//    }
//
//    @GetMapping("/xml-content")
//    public String getXmlContent() throws IOException {
//        File xmlFile = zipDownloaderService.downloadAndExtractXml(
//                "https://bdu.fstec.ru/files/documents/vulxml.zip");
//
//        return FileUtils.readFileToString(xmlFile, StandardCharsets.UTF_8);
//    }
}
