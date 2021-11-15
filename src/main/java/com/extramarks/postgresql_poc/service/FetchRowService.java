package com.extramarks.postgresql_poc.service;

import org.flywaydb.core.api.migration.Context;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class FetchRowService {
    public ResultSet getResultSet(Context context, String table) throws SQLException {
        Statement stmt = context.getConnection().createStatement();

        ResultSet fetchedRows = stmt.executeQuery("SELECT * FROM " + table);

        return fetchedRows;
    }
}
