package database;

import java.sql.*;
import java.text.SimpleDateFormat;

public class DatabaseHelper {
    private final String hostname = "localhost";
    private final int port = 3306;
    private final String database = "trongame";
    private final String table = "winners";
    private Connection conn = null;

    private final String sqlUsername = "trongame";
    private final String sqlPassword = "TronGame123";


    public DatabaseHelper() throws DatabaseException {
        log("Connecting to database..", false);
        String url = "jdbc:mysql://" + hostname + ":" + port;
        try{
            // trying to use the jbdc drier
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            try{
                // trying to connect to server
                this.conn = DriverManager.getConnection(url, sqlUsername, sqlPassword);
                log("Database connected!", false);
                log("Checking if database and table is present..", false);

                try{
                    // trying to set database to trongame
                    conn.setCatalog(database);
                    log("Database exists, switching to it", false);
                }catch (SQLException e){
                    if(e.getErrorCode() == 1049){
                        log("Database is not yet created, attemping to create..", false);
                        Statement stmt = conn.createStatement();
                        try{
                            // trying to create database
                            stmt.executeUpdate("CREATE DATABASE " + database);
                        }catch (SQLException e2){
                            log("Could not create database: " + e2.getMessage(), true);
                            throw new DatabaseException("Could not create database: " + e2.getMessage());
                        }
                        log("Database '" + database + "' created successfully, switching to it..", false);
                        conn.setCatalog(database);
                        log("Switched to '" + database + "'", false);
                    }else{
                        log("An error has occurred: " + e.getMessage(), true);
                        throw new DatabaseException("An error has occurred: " + e.getMessage());
                    }
                }

                try{
                    log("Checking if '" + table + "' table exists...", false);
                    Statement stmt = conn.createStatement();
                    stmt.executeQuery("SELECT * FROM " + table);
                    log("Table '" + table + "' does exist", false);
                }catch (SQLException e){
                    if(e.getErrorCode() == 1146){
                        // table does not exists
                        log("Table '" + table + "' does not exists, creating it..", false);
                        try{
                            Statement stmt = conn.createStatement();
                            stmt.execute("CREATE TABLE " + table + " (id INT NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, points INT NOT NULL, PRIMARY KEY (id));");
                            log("Table '" + table + "' created successfully", false);
                        }catch (SQLException e2){
                            log("Could not create table '" + table + "': " + e2.getMessage(), true);
                            throw new DatabaseException("Could not create table '" + table + "': " + e2.getMessage());
                        }
                    }else{
                        log("An error has occurred: " + e.getMessage(), true);
                        throw new DatabaseException("An error has occurred: " + e.getMessage());
                    }
                }

            }catch (SQLException e){
                log("An error has ocurred: " + e.getMessage(), true);
                throw new DatabaseException("An error has ocurred: " + e.getMessage());
            }
        }catch (ClassNotFoundException | InstantiationException | IllegalAccessException e){
            log("Class jbdcDriver not found/could not be loaded!", true);
            throw new DatabaseException("Class jbdcDriver not found/could not be loaded!");
        }

        log("Database structure checked, queries can be executed.", false);
    }

    private void log(String msg, boolean error){
        if(error){
            System.err.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][DB][ERROR] " + msg);
        }else{
            System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][DB] " + msg);
        }

    }
}
