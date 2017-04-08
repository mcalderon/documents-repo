package com.firstfactory.api.storage;

import com.firstfactory.api.exception.DocumentHandlerException;
import com.firstfactory.api.util.ConfigProperties;
import com.firstfactory.api.util.DefaultConfigProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.stream.Collectors;

public class DocumentStorage {

    private ConfigProperties properties;
    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (%s)";

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
        try{
            final Connection connection = this.connectToDB();
            final String columns = record.keySet().stream().collect(Collectors.joining(","));
            final StringBuilder values = new StringBuilder();
            record.values().forEach(v -> values.append( (v instanceof String) ? "'" + v + "'," : v + ","));
            values.setLength(values.length() - 1);
            final String query = String.format(INSERT_QUERY, table, columns, values.toString());
            try(Statement statement = connection.createStatement()){
                return statement.executeUpdate(query);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DocumentHandlerException(e.getMessage(), e);
        }
    }
}
