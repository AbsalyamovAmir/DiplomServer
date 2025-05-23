package ru.udsu.diplomserver.utils;

import lombok.experimental.UtilityClass;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class HtmlParser {
    public static class VulnerabilityHtml {
        private String bdu;
        private String fileslist;

        public VulnerabilityHtml(String bdu, String fileslist) {
            this.bdu = bdu;
            this.fileslist = fileslist;
        }

        public String getBdu() {
            return bdu;
        }

        public String getFileslist() {
            return fileslist;
        }
    }

    public List<VulnerabilityHtml> parseHtml(String filePath) {
        try {
            File input = new File(filePath);
            Document doc = Jsoup.parse(input, "UTF-8");

            Elements rows = doc.select("table.vulnerabilitiesTbl tr");

            List<VulnerabilityHtml> vulnerabilities = new ArrayList<>();

            for (Element row : rows) {
                Element bduCell = row.selectFirst("td.bdu");
                if (bduCell != null) {
                    String bdu = bduCell.text();

                    Element nextRow = row.nextElementSibling();
                    if (nextRow != null) {
                        Element fileslistCell = nextRow.selectFirst("td.desc.fileslist");
                        if (fileslistCell != null) {
                            String fileslist = fileslistCell.text() + "&&&";
                            vulnerabilities.add(new VulnerabilityHtml(bdu, fileslist));
                        }
                    }
                }
            }
            return vulnerabilities;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}