package ru.omstu.service;

public interface DataExtractorService {
    String getType();

    String extract(String data, String path);
}