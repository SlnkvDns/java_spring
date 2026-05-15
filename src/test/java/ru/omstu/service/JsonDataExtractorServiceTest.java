package ru.omstu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class JsonDataExtractorServiceTest {

    private JsonDataExtractorService service;

    @BeforeEach
    void setUp() {
        service = new JsonDataExtractorService();
    }

    @Test
    @DisplayName("getType() возвращает 'json'")
    void getType_returnsJson() {
        assertEquals("json", service.getType());
    }

    @Test
    @DisplayName("Извлечение простого строкового значения")
    void extract_simpleStringValue() {
        String json = "{\"user\": {\"name\": \"Alex\"}}";
        String result = service.extract(json, "user/name");
        assertEquals("Alex", result);
    }

    @Test
    @DisplayName("Извлечение вложенного значения")
    void extract_nestedValue() {
        String json = "{\"a\": {\"b\": {\"c\": \"d\"}}}";
        assertEquals("d", service.extract(json, "a/b/c"));
    }
}