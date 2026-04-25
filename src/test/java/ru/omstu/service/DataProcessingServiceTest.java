package ru.omstu.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class DataProcessingServiceTest {

    @Autowired
    private DataProcessingService dataProcessingService;

    @Autowired
    private CacheService cacheService;

    @BeforeEach
    void clearCache() {
        cacheService.clear();
    }


    @Test
    @DisplayName("Для типа 'json' используется JsonDataExtractorService")
    void process_jsonType_returnsCorrectValue() {
        String result = dataProcessingService.process(
                "json",
                "{\"user\": {\"name\": \"Alex\"}}",
                "user/name"
        );
        assertEquals("Alex", result);
    }

    @Test
    @DisplayName("Для типа 'xml' используется XmlDataExtractorService")
    void process_xmlType_returnsCorrectValue() {
        String xml = "<root><user><name>Alex</name></user></root>";
        String result = dataProcessingService.process("xml", xml, "user/name");
        assertEquals("Alex", result);
    }

    @Test
    @DisplayName("Для типа 'yaml' используется YamlDataExtractorService")
    void process_yamlType_returnsCorrectValue() {
        String yaml = "relation:\n  - id: 2\n    name: Петр Иванов";
        String result = dataProcessingService.process("yaml", yaml, "relation/0/name");
        assertEquals("Петр Иванов", result);
    }

    @Test
    @DisplayName("Неизвестный тип выбрасывает IllegalArgumentException")
    void process_unknownType_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> dataProcessingService.process("csv", "data", "path"));
    }

    @Test
    @DisplayName("После первого запроса результат помещается в кеш")
    void cache_afterFirstRequest_cacheIsPopulated() {
        assertEquals(0, cacheService.size());

        dataProcessingService.process("json", "{\"x\": 1}", "x");

        assertEquals(1, cacheService.size());
    }

    @Test
    @DisplayName("Повторный идентичный запрос возвращает результат из кеша")
    void cache_secondIdenticalRequest_returnsCachedValue() {
        String data  = "{\"user\": {\"name\": \"Alex\"}}";
        String path  = "user/name";

        String first  = dataProcessingService.process("json", data, path);
        String second = dataProcessingService.process("json", data, path);

        assertEquals(first, second);
        assertEquals(1, cacheService.size());
    }

    @Test
    @DisplayName("Разные запросы создают разные записи в кеше")
    void cache_differentRequests_separateCacheEntries() {
        dataProcessingService.process("json", "{\"a\": 1}", "a");
        dataProcessingService.process("json", "{\"b\": 2}", "b");

        assertEquals(2, cacheService.size());
    }
}