package ru.omstu.dto;

public class ExtractResponse {
    private String value;

    public ExtractResponse(String value) {
        this.value = value;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
