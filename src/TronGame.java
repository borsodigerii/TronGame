import canvas.TronBackground;
import canvas.TronCanvas;
import database.DatabaseException;
import database.DatabaseHelper;
import motorcycle.MotorcycleColor;

import java.text.SimpleDateFormat;

public class TronGame {
    private DatabaseHelper db;
    private MotorcycleColor pOne__color;
    private MotorcycleColor pTwo__color;

    private TronGUI gui;
    private TronCanvas canvas;
    private TronBackground canvasBack;

    public TronGame(TronGUI gui) throws DatabaseException {
        db = new DatabaseHelper();

        this.gui = gui;
    }

    public void launchGame(){
        canvas = new TronCanvas();
        canvasBack = new TronBackground();
        gui.generateGameSpace(canvas, canvasBack);
    }

    private void log(String msg, boolean error){
        if(error){
            System.err.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][GAME][ERROR] " + msg);
        }else{
            System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][GAME] " + msg);
        }

    }
}
