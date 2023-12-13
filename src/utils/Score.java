package utils;

public class Score {

    /**
     * The scoring player's name
     */
    public String playerName;


    /**
     * The score of the player
     */
    public int score;


    /**
     * Used to represent a palyer and his/her scores
     * @param playerName The player's name
     * @param score The player's score
     */
    public Score(String playerName, int score){
        this.playerName = playerName;
        this.score = score;
    }
}
