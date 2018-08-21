package com.sparkTech.cat;

import com.sparkTech.util.TablePrinter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Shubham A Gupta on 24-Jul-18.
 */
public class CancelJdbcSql implements Runnable {

    private Statement stmt = null;
    private String query;

    public CancelJdbcSql(Statement stmt,String query ) {
        this.stmt = stmt;
        this.query = query;
    }

    @Override
    public void run() {
        try {
            long startTime = System.currentTimeMillis();
            System.out.println("Executing query.....");


            if(!stmt.isClosed()){
                ResultSet resultSet = stmt.executeQuery(query);
                TablePrinter.printResultSet(resultSet, 10);
                System.out.println("Query is executed successfully ...........");
            }else {
                System.out.println("Statement is already closed.. exiting ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
