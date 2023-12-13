package database;

public class DatabaseExecutionException extends RuntimeException{

    /**
     * An exception used for database-execution-related errors and problematic situations. Since it is a runtime exception, the program can run freely even if it is thrown; and it also should be only thrown if a non-game-breaking database error has happened
     * @param msg The message about the happened error/situation
     */
    public DatabaseExecutionException(String msg){
        super(msg);
    }
}
