package com.firstfactory.api.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Document {

    private int guid;
    private String fileText;
    private String fileType;
    private String notes;
    private String fileDownloadLink;

    public Document(String fileText, String fileType, String notes) {
        this.fileText = fileText;
        this.fileType = fileType;
        this.notes = notes;
    }

    public static Document castFromMap(Map<String, Object> origin) {
        Document document = new Document();
        if(!origin.isEmpty()) {
            document.setGuid(Integer.parseInt(origin.get("id").toString()));
            document.setFileText(origin.get("name").toString());
            document.setFileType(origin.get("type").toString());
            document.setNotes(origin.get("note").toString());
            document.setFileDownloadLink("/documents/download/" + origin.get("id"));
        }
        return document;
    }

    public static Map<String, Object> castToMap(Document document) {
        Map<String, Object> map = new HashMap<>();
        if (document.getGuid() != 0) {
            map.put("id", document.getGuid());
        }
        map.put("name", document.getFileText());
        map.put("type", document.getFileType());
        map.put("note", document.getNotes());
        return map;
    }
}
