package ru.omstu;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;

public class XmlDataReader implements DataReader{
    @Override
    public String getValue(String filePath, String valuePath) {
        try {
            XmlMapper mapper = new XmlMapper();
            JsonNode tree = mapper.readTree(new File(filePath));
            return tree.at(valuePath).asText();
        }
        catch (Exception e) {
            return "Ошибка:" + e.getMessage();
        }
    }
}
