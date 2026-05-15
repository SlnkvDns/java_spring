package ru.omstu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CacheService {

    private static final Logger log = LoggerFactory.getLogger(CacheService.class);

    private final Map<String, String> cache = new HashMap<>();

    public String buildKey(String type, String data, String path) {
        return type + ":" + path + ":" + data;
    }

    public String get(String key) {
        String value = cache.get(key);
        if (value != null) {
            log.info("Данные уже есть в кэше  | key='{}' | value='{}'", key, value);
        } else {
            log.info("Данные отсутствуют в кэше | key='{}'", key);
        }
        return value;
    }

    public void put(String key, String value) {
        cache.put(key, value);
        log.info("Положили в кэш  | key='{}' | value='{}'", key, value);
    }

    public int size() {
        return cache.size();
    }

    public void clear() {
        cache.clear();
        log.info("Кэш очищен");
    }
}