package com.example.MajorProject;
import java.sql.*;

public class DatabaseConnection {
    private static final String databaseUrl ="jdbc:mysql://localhost:3306/new_schema1";
    private static final String userName = "root";
    private static final String password ="F501T15S70@a";

    public Statement getStatement(){
        Statement statement = null;
        Connection conn;
        try{
            conn = DriverManager.getConnection(databaseUrl, userName, password);
            statement = conn.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
        return statement;
    }
    public ResultSet getQueryTable(String query){
        Statement statement = getStatement();
        try{
            return statement.executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int executeUpdateQuery(String query) {
        Statement statement = getStatement();
        try {
            return statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
