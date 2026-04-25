package ru.omstu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class YamlDataExtractorServiceTest {

    private YamlDataExtractorService service;

    @BeforeEach
    void setUp() {
        service = new YamlDataExtractorService();
    }

    @Test
    @DisplayName("getType() возвращает 'yaml'")
    void getType_returnsYaml() {
        assertEquals("yaml", service.getType());
    }

    @Test
    @DisplayName("Извлечение простого строкового значения из YAML")
    void extract_simpleStringValue() {
        String yaml = "user:\n  name: Alex";
        assertEquals("Alex", service.extract(yaml, "user/name"));
    }
}