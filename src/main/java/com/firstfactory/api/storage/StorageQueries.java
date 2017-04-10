package com.firstfactory.api.storage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
 final class StorageQueries {

    static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (%s)";
    static final String SELECT_ALL = "SELECT * FROM %s";
    static final String SELECT_BY_ID = "SELECT * FROM %s WHERE id = %d";
    static final String DELETE_BY_ID = "DELETE FROM %s WHERE id = %d";
}
