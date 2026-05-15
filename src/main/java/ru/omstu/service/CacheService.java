package ru.omstu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
@ConditionalOnProperty(name = "cache.type", havingValue = "map", matchIfMissing = true)
public class CacheService implements CacheStorageService {

    private static final Logger log = LoggerFactory.getLogger(CacheService.class);

    private final Map<String, String> cache = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> createdAtCache = new ConcurrentHashMap<>();


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
        createdAtCache.put(key, LocalDateTime.now());
        log.info("Положили в кэш  | key='{}' | value='{}'", key, value);
    }

    public int size() {
        return cache.size();
    }

    public void clear() {
        cache.clear();
        createdAtCache.clear();
        log.info("Кэш очищен");
    }

    @Override
    public void clearCreatedBefore(LocalDateTime time) {
        createdAtCache.forEach((key, createdAt) -> {
            if (createdAt.isBefore(time)) {
                cache.remove(key);
                createdAtCache.remove(key);
            }
        });
        log.info("Старые записи в кэше удалены");
    }
}
