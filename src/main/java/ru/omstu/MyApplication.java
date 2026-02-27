package ru.omstu;

public class MyApplication {
    public static void main(String[] args) {
        String jsonPath = "src/main/resources/file.json";
        String xmlPath = "src/main/resources/file.xml";

        DataReader jsonReader = DataReaderFactory.getDataReader(jsonPath);
        DataReader xmlReader = DataReaderFactory.getDataReader(xmlPath);

        System.out.println(jsonReader.getValue(jsonPath, "/name"));
        System.out.println(jsonReader.getValue(jsonPath, "/relation/1/name"));

        System.out.println(xmlReader.getValue(xmlPath, "/name"));
        System.out.println(xmlReader.getValue(xmlPath, "/relation/1/name"));
    }
}