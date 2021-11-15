package com.extramarks.postgresql_poc.service;

import org.springframework.stereotype.Component;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;

@Component
public class TargetIdService {
    public int getLastId(String table,String id) throws SQLException {
        int lastFetchedId = -1;

        String maxIdQuery = "SELECT max( "+id+" ) FROM  "+table;

        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet maxIdRowSet = factory.createCachedRowSet();

        String psqlUrl = "jdbc:postgresql://localhost:5432/Target";
        maxIdRowSet.setUrl(psqlUrl);
        maxIdRowSet.setUsername("root");
        maxIdRowSet.setPassword("utsav");
        maxIdRowSet.setCommand(maxIdQuery);
        maxIdRowSet.execute();

        if (maxIdRowSet.next()) lastFetchedId = maxIdRowSet.getInt(1);
        else throw new SQLException();

        System.out.println("Key returned from " + table + " 'SELECT LAST_INSERT_ID': " + lastFetchedId);
        return lastFetchedId;
    }
}
