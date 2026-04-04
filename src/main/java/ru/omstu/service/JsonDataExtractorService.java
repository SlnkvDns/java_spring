package ru.omstu.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonDataExtractorService implements DataExtractorService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getType() {
        return "json";
    }

    @Override
    public String extract(String data, String path) {
        try {
            JsonNode tree = objectMapper.readTree(data);
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
