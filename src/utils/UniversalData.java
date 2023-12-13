package utils;

import java.awt.*;

public class UniversalData {

    /**
     * The size of the main frame, and the program's window
     */
    private final static Dimension windowDimension = new Dimension(700, 700);


    /**
     * The duration of which a gameCycle lasts for
     */
    private final static int gameCycle = 50;


    /**
     * The size of the steps the motors are moved with in every cycle
     */
    private final static int motorSteps = 5;


    /**
     * The width of the bike sprite to render at
     */
    private final static int motorWidth = 50;


    /**
     * The height of the bike sprite to render at
     */
    private final static int motorHeight = 100;



    public static Dimension getWindowDimension(){
        return windowDimension;
    }
    public static int getGameCycle(){
        return gameCycle;
    }
    public static int getMotorWidth(){
        return motorWidth;
    }
    public static int getMotorHeight(){
        return motorHeight;
    }
    public static int getMotorSteps(){
        return motorSteps;
    }
}
