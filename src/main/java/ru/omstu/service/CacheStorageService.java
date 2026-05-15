package ru.omstu.service;

import java.time.LocalDateTime;

public interface CacheStorageService {

    String buildKey(String type, String data, String path);

    String get(String key);

    void put(String key, String value);

    int size();

    void clear();

    void clearCreatedBefore(LocalDateTime time);
}
