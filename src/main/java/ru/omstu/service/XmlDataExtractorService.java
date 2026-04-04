package ru.omstu.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

@Component
public class XmlDataExtractorService implements DataExtractorService {

    private final XmlMapper xmlMapper = new XmlMapper();

    @Override
    public String getType() {
        return "xml";
    }

    @Override
    public String extract(String data, String path) {
        try {
            JsonNode tree = xmlMapper.readTree(data);
            String jsonPointer = "/" + path;
            JsonNode result = tree.at(jsonPointer);
            if (result.isMissingNode()) {
                throw new IllegalArgumentException("Значение по пути '" + path + "' не найдено");
            }
            return result.asText();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
