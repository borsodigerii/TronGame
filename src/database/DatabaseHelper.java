package database;

import utils.Score;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static final String hostname = "localhost";
    private static final int port = 3306;
    private static final String database = "trongame";
    private static final String table = "winners";
    private Connection conn = null;
    private boolean canExecute = false;
    //TODO: execution block while an operation is in progress

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
                        // table does not exist
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
        canExecute = true;
    }

    public void increaseWinningPlayer(String pName) throws DatabaseExecutionException{
        log("Increasing winning count for '" + pName + "'...", false);
        if(!canExecute){
            throwCantExecuteMessage(null);
            return;
        }
        try{
            Statement stmt = conn.createStatement();
            String getPointsQuery = "SELECT count(points) as nameCount FROM " + table + " WHERE name='" + pName + "';";
            ResultSet resultPoints = stmt.executeQuery(getPointsQuery);
            resultPoints.next();
            if(resultPoints.getInt("nameCount") == 0){
                // no row with given user was created previously
                log("No winnings are yet recorded for user '" + pName + "', creating a row and inserting 1...", false);
                String insertQuery = "INSERT INTO " + table + " (name, points) VALUES ('" + pName + "', 1);";
                if(stmt.executeUpdate(insertQuery) > 0){
                    log("Insert was successfull.", false);
                }else{
                    log("Could not insert player into the database.", true);
                }
            }else{
                log("Player is already present in the database, incrementing the winnings accociated to him..", false);
                String getPoint = "SELECT points FROM " + table + " WHERE name='" + pName + "' LIMIT 1;";
                ResultSet pointResult = stmt.executeQuery(getPoint);
                pointResult.next();
                int point = pointResult.getInt("points");

                String updateQuery = "UPDATE " + table + " SET points=" + (point+1) + " WHERE name='" + pName + "';";
                if(stmt.executeUpdate(updateQuery) > 0){
                    log("Successfully incremented winnings from " + point + " to " + (point+1) + " for player '" + pName + "'", false);
                }else{
                    log("Could not increment winnings for player '" + pName + "'", false);
                }
            }
        }catch (SQLException e){
            throwCantExecuteMessage(e.getMessage());
        }

    }

    public List<Score> getScores(){
        log("Retrieving the scoreboard serialized data..", false);
        if(!canExecute){
            throwCantExecuteMessage(null);
            return null;
        }

        try{
            List<Score> scoreList = new ArrayList<Score>();
            Statement stmt = conn.createStatement();
            String query = "SELECT name, points FROM " + table + " ORDER BY points DESC;";
            ResultSet results = stmt.executeQuery(query);
            while(results.next()){
                scoreList.add(new Score(results.getString("name"), results.getInt("points")));
            }
            return scoreList;
        }catch (SQLException e){
            throwCantExecuteMessage(e.getMessage());
            return null;
        }
    }





    private void throwCantExecuteMessage(String msg) throws DatabaseExecutionException {
        throw new DatabaseExecutionException("The connection between the database and the game couldn't been established, therefore no queries can be executed." + (msg != null ? " The error message: " + msg : ""));
    }
    private void log(String msg, boolean error){
        if(error){
            System.err.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][DB][ERROR] " + msg);
        }else{
            System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][DB] " + msg);
        }

    }
}
