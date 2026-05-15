package ru.omstu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataProcessingService {

    private final Map<String, DataExtractorService> extractors;
    private final CacheService cacheService;

    @Autowired
    public DataProcessingService(List<DataExtractorService> extractorList,
                                 CacheService cacheService) {
        this.extractors = extractorList.stream()
                .collect(Collectors.toMap(DataExtractorService::getType, e -> e));
        this.cacheService = cacheService;
    }

    public String process(String type, String data, String path) {
        DataExtractorService extractor = extractors.get(type.toLowerCase());
        if (extractor == null) {
            throw new IllegalArgumentException("Неподдерживаемый тип: '" + type + "'");
        }


        String key = cacheService.buildKey(type.toLowerCase(), data, path);
        String cached = cacheService.get(key);
        if (cached != null) {
            return cached;
        }

        String result = extractor.extract(data, path);
        cacheService.put(key, result);
        return result;
    }
}
