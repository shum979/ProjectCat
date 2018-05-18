package com.sapient.cat;

import com.sapient.util.TablePrinter;

import java.sql.*;

/**
 * Created by Shubham A Gupta on 01-May-18.
 */
public class SampleJdbcClient {

    static AppContext appContext = null;
    static Connection connection = null;

    /**
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {

        try {
            initialize(args);
            createConnection();
            appContext.getAllValuesContaining("sample.query").forEach(query -> runQuery(query));

        }finally {
            connection.close();
        }
    }


    private static void runQuery(String identifier){
        String query = appContext.getValue(identifier);
        System.out.println("Query --> " + query);

        // logging time -- start
        long startTime = System.currentTimeMillis();

        ResultSet resultSet = executeQuery(query);
        TablePrinter.printResultSet(resultSet,10);

        // logging end time
        long endTime = System.currentTimeMillis();
        System.out.println("query finished in " + ((endTime - startTime )/1000.0) + " seconds");
        System.out.println();
    }


    private static void initialize(String[] args){
        if(args.length != 1 ){
            throw new IllegalArgumentException("Property file not specified");
        }

        String filePath = args[0];
        appContext = AppContext.getInstance(filePath);
    }


    private static void createConnection() throws SQLException{
        String url = appContext.getValue("jdbc.url");
        String user = appContext.getValue("user.name");
        String password = appContext.getValue("user.password");
        System.out.printf("Using url : %s with userName : %s and Password : %s",url,user,password);

        try {
            Class.forName(appContext.getValue("driver.name"));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        connection = DriverManager.getConnection(url, user,password);
    }


    private static ResultSet executeQuery(String sql){
        ResultSet resultSet = null;

        try {
            Statement stmt = connection.createStatement();
            resultSet = stmt.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}
