package motorcycle;


import utils.UniversalData;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;

public class MotorCyclePosition {
    private int x;
    private int y;

    public MotorCyclePosition(int x, int y){
        if(x < UniversalData.getWindowDimension().width || x > UniversalData.getWindowDimension().width || y < UniversalData.getWindowDimension().height || y > UniversalData.getWindowDimension().height){
            log("Invalid motor positions were specified.", true);
            throw new InvalidParameterException("Invalid motor positions were specified.");
        }
        this.x = x;
        this.y = y;
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
}
