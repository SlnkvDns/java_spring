package ru.omstu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CacheSchedulerService {

    private static final Logger log = LoggerFactory.getLogger(CacheSchedulerService.class);

    private final CacheStorageService cacheService;

    public CacheSchedulerService(CacheStorageService cacheService) {
        this.cacheService = cacheService;
    }

    @Scheduled(fixedRate = 30000)
    public void clearOldCacheEntries() {
        try {
            cacheService.clearCreatedBefore(LocalDateTime.now().minusSeconds(60));
        } catch (Exception exception) {
            log.error("Ошибка при удалении старых записей кэша", exception);
        }
    }

    @Scheduled(cron = "0 0 0 ? * SUN")
    public void clearAllCacheEntries() {
        try {
            cacheService.clear();
        } catch (Exception exception) {
            log.error("Ошибка при полной очистке кэша", exception);
        }
    }
}
