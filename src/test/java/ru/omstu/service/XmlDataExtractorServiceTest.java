package ru.omstu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class XmlDataExtractorServiceTest {

    private XmlDataExtractorService service;

    @BeforeEach
    void setUp() {
        service = new XmlDataExtractorService();
    }

    @Test
    @DisplayName("getType() возвращает 'xml'")
    void getType_returnsXml() {
        assertEquals("xml", service.getType());
    }

    @Test
    @DisplayName("Извлечение простого значения из XML")
    void extract_simpleValue() {
        String xml = "<root><user><name>Alex</name></user></root>";
        assertEquals("Alex", service.extract(xml, "user/name"));
    }

    @Test
    @DisplayName("Извлечение вложенного значения из XML")
    void extract_nestedValue() {
        String xml = "<root><a><b>deep</b></a></root>";
        assertEquals("deep", service.extract(xml, "a/b"));
    }
}