package ru.omstu.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.stereotype.Component;

@Component
public class YamlDataExtractorService implements DataExtractorService {

    private final YAMLMapper yamlMapper = new YAMLMapper();

    @Override
    public String getType() {
        return "yaml";
    }

    @Override
    public String extract(String data, String path) {
        try {
            JsonNode tree = yamlMapper.readTree(data);
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
