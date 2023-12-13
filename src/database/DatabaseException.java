package database;

public class DatabaseException extends Exception {

    /**
     * An exception used for database-related errors and problematic situations.
     * @param msg The message about the happened error/situation
     */
    public DatabaseException(String msg){
        super(msg);
    }
}
