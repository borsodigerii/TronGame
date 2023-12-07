import database.DatabaseException;
import database.DatabaseHelper;

import java.text.SimpleDateFormat;

public class TronGame {
    private DatabaseHelper db;
    private MotorcycleColor pOne__color;
    private MotorcycleColor pTwo__color;

    private TronGUI gui;
    private TronCanvas canvas;

    public TronGame(TronGUI gui) throws DatabaseException {
        db = new DatabaseHelper();

        this.gui = gui;
    }

    public void launchGame(){
        canvas = new TronCanvas();
        gui.generateGameSpace(canvas);
    }

    private void log(String msg){
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "] " + msg);
    }
    private void log(String msg, boolean error){
        if(error){
            System.err.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][ERROR] " + msg);
        }else{
            System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "] " + msg);
        }

    }
}
