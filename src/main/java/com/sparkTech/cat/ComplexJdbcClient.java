package com.sparkTech.cat;

import com.sparkTech.util.TablePrinter;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Shubham A Gupta on 24-Jul-18.
 */
public class ComplexJdbcClient {

    static AppContext appContext = null;
    static Connection connection = null;

    /**
     * @param args
     * @throws SQLException
     */



    public static void main(String[] args) throws SQLException {

        ExecutorService ec = Executors.newSingleThreadExecutor();

        try {
            initialize(args);
            createConnection();

            String query = appContext.getValue("sample.query1");
            Statement stmt = connection.createStatement();

            long waitTime = Long.parseLong(appContext.getValue("wait.time.milli"));

            ec.submit(new CancelJdbcSql(stmt,query));
            Thread.sleep(waitTime);

            if(!stmt.isClosed()) {

                System.out.println("cancelling query ....");
                stmt.cancel();
                System.out.println("query is cancelled .......");

            }else {
                System.out.println("connection is closed .. exiting now.. ");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            connection.close();
            ec.shutdownNow();

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
            System.out.println("creating Connection ...");
            Class.forName(appContext.getValue("driver.name"));

            connection = DriverManager.getConnection(url, user,password);
            System.out.println("Connection created successfully " + connection.toString());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }



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
