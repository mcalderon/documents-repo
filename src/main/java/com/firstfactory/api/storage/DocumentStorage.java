package com.firstfactory.api.storage;

import com.firstfactory.api.exception.DocumentHandlerException;
import com.firstfactory.api.util.ConfigProperties;
import com.firstfactory.api.util.DefaultConfigProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//TODO this should implement a interface
public class DocumentStorage {

    private ConfigProperties properties;
    //TODO this should be move to a different file, it has the potential of grow a lot
    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String SELECT_ALL = "SELECT * FROM %s";
    private static final String SELECT_BY_ID = "SELECT * FROM %s WHERE id = %d";
    private static final String DELETE_BY_ID = "DELETE FROM %s WHERE id = %d";

    public DocumentStorage() {
        properties = new DefaultConfigProperties();
    }

    private Connection connectToDB() throws ClassNotFoundException, SQLException {
        final String url = "jdbc:postgresql://" + this.properties.getProperty("documents.database.host") + ":5432/" +
                this.properties.getProperty("documents.database.name") + "?user=" +
                this.properties.getProperty("documents.database.username") + "&password=" +
                this.properties.getProperty("documents.database.password") +
                "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url);
    }

    public int insertRecord(String table, Map<String, Object> record) {
        try {
            final Connection connection = this.connectToDB();
            final String columns = record.keySet().stream().collect(Collectors.joining(","));
            final StringBuilder values = new StringBuilder();
            record.values().forEach(v -> values.append((v instanceof String) ? "'" + v + "'," : v + ","));
            values.setLength(values.length() - 1);
            final String query = String.format(INSERT_QUERY, table, columns, values.toString());
            try (Statement statement = connection.createStatement()) {
                return statement.executeUpdate(query);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DocumentHandlerException(e.getMessage(), e);
        }
    }

    public int deleteRecord(String table, int id) {
        final int affectedRows;
        final String query = String.format(DELETE_BY_ID, table, id);
        try {
            final Connection connection = this.connectToDB();
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                affectedRows = statement.executeUpdate();
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DocumentHandlerException(e.getMessage(), e);
        }
        return affectedRows;
    }

    public Map<String, Object> findById(String table, int id) {
        final Map<String, Object> record = new HashMap<>();
        final String query = String.format(SELECT_BY_ID, table, id);
        try {
            final Connection connection = this.connectToDB();
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                final ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    this.extractSingleRecordRow(resultSet).forEach(record::put);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DocumentHandlerException(e.getMessage(), e);
        }
        return record;
    }

    public List<Map<String, Object>> findAll(String table) {
        try {
            final List<Map<String, Object>> results = new ArrayList<>();
            final Connection connection = this.connectToDB();
            final String query = String.format(SELECT_ALL, table);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                final ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    results.add(this.extractSingleRecordRow(resultSet));
                }
            }
            return results;
        } catch (ClassNotFoundException | SQLException e) {
            throw new DocumentHandlerException(e.getMessage(), e);
        }
    }

    private Map<String, Object> extractSingleRecordRow(ResultSet resultSet) throws SQLException {
        final ResultSetMetaData info = resultSet.getMetaData();
        final int columnCount = info.getColumnCount();
        Map<String, Object> record = new HashMap<>();
        IntStream.rangeClosed(1, columnCount).forEach(i -> {
            try {
                record.put(info.getColumnName(i), resultSet.getObject(i));
            } catch (SQLException e) {
                throw new DocumentHandlerException(e.getMessage(), e);
            }
        });
        return record;
    }
}
