package ru.omstu;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class JsonDataReader implements DataReader{
    @Override
    public String getValue(String filePath, String valuePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tree = mapper.readTree(new File(filePath));
            return tree.at(valuePath).asText();

        } catch (Exception e) {
            return "Ошибка: " + e.getMessage();
        }
    }
}
