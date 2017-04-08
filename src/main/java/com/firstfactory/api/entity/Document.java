package com.firstfactory.api.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Document {

    private int id;
    private String name;
    private String type;
    private String notes;

    public Document(String name, String type, String notes) {
        this.name = name;
        this.type = type;
        this.notes = notes;
    }

    public static Document castFromMap(Map<String, Object> origin) {
        Document document = new Document();
        document.setId(Integer.parseInt(origin.get("id").toString()));
        document.setName(origin.get("name").toString());
        document.setType(origin.get("type").toString());
        document.setNotes(origin.get("note").toString());
        return document;
    }

    public static Map<String, Object> castToMap(Document document) {
        Map<String, Object> map = new HashMap<>();
        if (document.getId() != 0) {
            map.put("id", document.getId());
        }
        map.put("name", document.getName());
        map.put("type", document.getType());
        map.put("note", document.getNotes());
        return map;
    }
}
