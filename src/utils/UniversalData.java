package utils;

import java.awt.*;

public class UniversalData {
    private final static Dimension windowDimension = new Dimension(700, 700);
    private final static int gameCycle = 50;
    private final static int motorSteps = 5;

    private final static int motorWidth = 50;
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
