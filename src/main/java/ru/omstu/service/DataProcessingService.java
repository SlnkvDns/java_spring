package ru.omstu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataProcessingService {
    private final Map<String, DataExtractorService> extractors;

    @Autowired
    public DataProcessingService(List<DataExtractorService> extractorList) {
        this.extractors = extractorList.stream()
                .collect(Collectors.toMap(DataExtractorService::getType, e -> e));
    }

    public String process(String type, String data, String path) {
        DataExtractorService extractor = extractors.get(type.toLowerCase());
        if (extractor == null) {
            throw new IllegalArgumentException("Неподдерживаемый тип: '" + type + "'");
        }
        return extractor.extract(data, path);
    }
}
