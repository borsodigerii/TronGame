package motorcycle;


import utils.UniversalData;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;

public class MotorCyclePosition {
    private int x;
    private int y;
    private int facing;

    public MotorCyclePosition(int x, int y, int facing){
        if(x < 0 || x > UniversalData.getWindowDimension().width || y < 0 || y > UniversalData.getWindowDimension().height){
            log("Invalid motor positions were specified.", true);
            throw new InvalidParameterException("Invalid motor positions were specified.");
        }
        this.x = x;
        this.y = y;
        this.facing = facing;
    }

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
