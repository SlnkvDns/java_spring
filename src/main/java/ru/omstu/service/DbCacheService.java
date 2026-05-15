package ru.omstu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.omstu.figprogwork.db.CacheEntity;
import ru.omstu.figprogwork.db.CacheRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@ConditionalOnProperty(name = "cache.type", havingValue = "db")
public class DbCacheService implements CacheStorageService {

    private static final Logger log = LoggerFactory.getLogger(DbCacheService.class);

    private final CacheRepository cacheRepository;

    public DbCacheService(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    @Override
    public String buildKey(String type, String data, String path) {
        return type + ":" + path + ":" + data;
    }

    @Override
    @Transactional(readOnly = true)
    public String get(String key) {
        Optional<CacheEntity> cacheEntity = cacheRepository.findById(key);
        if (cacheEntity.isPresent()) {
            log.info("Данные уже есть в кэше  | key='{}' | value='{}'", key, cacheEntity.get().getValue());
            return cacheEntity.get().getValue();
        }

        log.info("Данные отсутствуют в кэше | key='{}'", key);
        return null;
    }

    @Override
    @Transactional
    public void put(String key, String value) {
        CacheEntity cacheEntity = new CacheEntity();
        cacheEntity.setKey(key);
        cacheEntity.setValue(value);
        cacheEntity.setCreatedAt(LocalDateTime.now());
        cacheRepository.save(cacheEntity);
        log.info("Положили в кэш  | key='{}' | value='{}'", key, value);
    }

    @Override
    @Transactional(readOnly = true)
    public int size() {
        return (int) cacheRepository.count();
    }

    @Override
    @Transactional
    public void clear() {
        cacheRepository.deleteAll();
        log.info("Кэш очищен");
    }

    @Override
    @Transactional
    public void clearCreatedBefore(LocalDateTime time) {
        cacheRepository.deleteByCreatedAtBefore(time);
        log.info("Старые записи в кэше удалены");
    }
}
