package motorcycle;


import utils.UniversalData;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;

public class MotorCyclePosition {

    /**
     * The x coordinate for the position
     */
    private int x;


    /**
     * The y coordinate for the position
     */
    private int y;


    /**
     * The degree that which the motor is facing currently. Can be 0, 90, 180, 270
     */
    private int facing;


    /**
     * This class is used for storing the position and the degree the motors face towards in one datastructure
     * @param x The x coordinate
     * @param y The y coordinate
     * @param facing The degree the motor is facing towards. Can be 0, 90, 180 and 270
     */
    public MotorCyclePosition(int x, int y, int facing){
        if(x < 0 || x > UniversalData.getWindowDimension().width || y < 0 || y > UniversalData.getWindowDimension().height){
            log("Invalid motor positions were specified.", true);
            throw new InvalidParameterException("Invalid motor positions were specified.");
        }
        this.x = x;
        this.y = y;
        this.facing = facing;
    }


    /**
     * Logging utility, for easier reporting to the console
     * @param msg The message to log
     * @param error True if it is an error, false otherwise
     */
    private void log(String msg, boolean error){
        if(error){
            System.err.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][MOTORPOS][ERROR] " + msg);
        }else{
            System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][MOTORPOS] " + msg);
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFacing() {
        return facing;
    }

    public void setX(int x) {
        if(x < 0 || x > UniversalData.getWindowDimension().width){
            return;
        }
        this.x = x;
    }

    public void setY(int y) {
        if(y < 0 || y > UniversalData.getWindowDimension().height){
            return;
        }
        this.y = y;
    }

    public void setFacing(int facing){
        this.facing = facing;
    }
}
