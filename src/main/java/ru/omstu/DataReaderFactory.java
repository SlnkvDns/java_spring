package ru.omstu;

public class DataReaderFactory {
    public static DataReader getDataReader(String filePath) {
        if (filePath.endsWith(".json")) {
            return new JsonDataReader();
        } else if (filePath.endsWith(".xml")) {
            return new XmlDataReader();
        }
        throw new IllegalArgumentException("Неподдерживаемый формат файла");
    }
}
