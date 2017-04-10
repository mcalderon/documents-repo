package com.firstfactory.api.storage;

import java.util.List;
import java.util.Map;

public interface DocumentStorage {

    int insertRecord(String table, Map<String, Object> record);

    int deleteRecord(String table, int id);

    Map<String, Object> findById(String table, int id);

    List<Map<String, Object>> findAll(String table);
}
