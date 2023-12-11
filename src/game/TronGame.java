package game;

import canvas.TronBackground;
import canvas.TronCanvas;
import database.DatabaseException;
import database.DatabaseHelper;
import motorcycle.MotorCyclePosition;
import motorcycle.MotorcycleColor;
import utils.Keys;
import utils.Score;
import utils.UniversalData;

import java.text.SimpleDateFormat;
import java.util.List;

public class TronGame {
    private final DatabaseHelper db;
    public static MotorcycleColor pOne__color;
    public static MotorcycleColor pTwo__color;
    private boolean isGameGoing = false;

    private String p1Name;
    private String p2Name;

    public static MotorCyclePosition m1 = new MotorCyclePosition(50, UniversalData.getWindowDimension().height - 200, 90);
    public static MotorCyclePosition m2 = new MotorCyclePosition(UniversalData.getWindowDimension().width - 100, 50, 270);

    public static final int MotorModelOffsetHorizontal = 7;
    public static final int MotorModelOffsetVertical = 5;
    private final TronGUI gui;
    private TronCanvas canvas;

    public TronGame(TronGUI gui) throws DatabaseException {
        db = new DatabaseHelper();

        this.gui = gui;
    }

    public void launchGame(String p1, String p2, MotorcycleColor p1Color, MotorcycleColor p2Color) {
        isGameGoing = false;
        m1 = new MotorCyclePosition(50, UniversalData.getWindowDimension().height - 200, 90);
        m2 = new MotorCyclePosition(UniversalData.getWindowDimension().width - 100, 50, 270);
        pOne__color = p1Color;
        pTwo__color = p2Color;
        canvas = new TronCanvas();
        TronBackground canvasBack = new TronBackground();
        canvas.resetMotorPath();
        gui.generateGameSpace(canvas, canvasBack, this::gameLaunched, this);
        p1Name = p1;
        p2Name = p2;
    }

    public void gameLaunched() {
        log("Game started", false);
        m1 = new MotorCyclePosition(50, canvas.getHeight() - 200, 90);
        m2 = new MotorCyclePosition(canvas.getWidth() - 100, 50, 270);
        isGameGoing = true;
        gameCycle();
    }

    private void gameCycle() {
        setTimeout(() -> {
            // Orientation and deciding
            int m1x = m1.getX();
            int m1y = m1.getY();
            int m2x = m2.getX();
            int m2y = m2.getY();


            //m1
            if (m1.getFacing() == 0) {
                m1.setX(m1.getX() + 5);
            } else if (m1.getFacing() == 90) {
                m1.setY(m1.getY() - 5);
            } else if (m1.getFacing() == 180) {
                m1.setX(m1.getX() - 5);
            } else if (m1.getFacing() == 270) {
                m1.setY(m1.getY() + 5);
            }


            //m2
            if (m2.getFacing() == 0) {
                m2.setX(m2.getX() + 5);
            } else if (m2.getFacing() == 90) {
                m2.setY(m2.getY() - 5);
            } else if (m2.getFacing() == 180) {
                m2.setX(m2.getX() - 5);
            } else if (m2.getFacing() == 270) {
                m2.setY(m2.getY() + 5);
            }

           /*canvas.addPathToMotors(1, m1x, m1y);
           canvas.addPathToMotors(2, m2x, m2y);*/

            if (checkWinning() == 3) {
                // draw
                log("The game ended, result is draw", false);
                gui.generateWinningScreen(null);
                isGameGoing = false;
                return;
            } else if (checkWinning() == 2) {
                // p2 won
                log("The game ended, result is p2 winning", false);
                db.increaseWinningPlayer(p2Name);
                gui.generateWinningScreen(p2Name);
                isGameGoing = false;
                return;
            } else if (checkWinning() == 1) {
                // p1 won
                log("The game ended, result is p1 winning", false);
                db.increaseWinningPlayer(p1Name);
                gui.generateWinningScreen(p1Name);
                isGameGoing = false;
                return;
            }


            canvas.addPathToMotors(1, m1.getX(), m1.getY());
            canvas.addPathToMotors(2, m2.getX(), m2.getY());

            gui.canvasContent.repaint();
            // canvas.repaint();
            if(isGameGoing) gameCycle();
        }, UniversalData.getGameCycle());

    }

    private int checkWinning() {
        boolean draw = false;
        boolean p1Won = false;
        boolean p2Won = false;
        boolean outOfFrame = false;
        //TODO: optimalization: it would be enough to check the margin/outer 1-2px's of the motor for collision, and not the whole motor body
        int m1x0, m1x1, m1y0, m1y1, m2x0, m2x1, m2y0, m2y1, width, height;
        // mNx0 - starting coordinate, x tag
        // mNy0 - starting coordinate, y tag
        // mNx1 - ending coordinate, x tag
        // mNy1 - ending coordinate, y tag




        //MOTOR1

        boolean won = false;

        // in the following codes, a MotorModelOffset is used to determine some outer bounds, because the model is not exactly sized down to the exact sizes of the motor, and some empty spacing is left around it. Without the offset, the game would sense a collision, while visually the line and the motor model did not in fact reached each other
        switch (m1.getFacing()){
            case 0:
                width = UniversalData.getMotorHeight();
                height = UniversalData.getMotorWidth();
                // based on the following line, we calculate the motor's drawn dimensions, and check if any colored pixel is inside of these dimensions
                // g.drawImage(motor1, m1Pos.getX(), m1Pos.getY() + (width/4), width, -height, this);
                m1x0 = m1.getX();
                m1y0 = m1.getY() + (width/4);
                //---
                m1x1 = m1x0 + width;
                m1y1 = m1y0 - height;

                if(isMotorOutOfFrame(m1x0, m1y0, m1x1, m1y1)){
                    p2Won = true;
                    outOfFrame = true;
                    break;
                }

                // any colored pixel that is inside the rectangle of (m1x0,m1y0)-(m1x1,m1y1) is considered a collision
                // in this case, we will go from the top right end of the motorcycle, since it is moving to the right and collisions are more likely to happen from the right side, rather from the left

                for(int n = m1x1 - MotorModelOffsetHorizontal; n > m1x0; n--){
                    for(int m = m1y0; m > m1y1; m--){
                        if(canvas.getPathToMotors(n, m) != 0){
                            won = true;
                            break;
                        }
                    }
                    if(won) break;
                }
                if(won){
                    p2Won = true;
                }

                break;
            case 90:
                width = UniversalData.getMotorWidth();
                height = UniversalData.getMotorHeight();
                // based on the following line, we calculate the motor's drawn dimensions, and check if any colored pixel is inside of these dimensions
                // g.drawImage(motor1, m1Pos.getX()-(width/2), m1Pos.getY(), width, -height, this);

                m1x0 = m1.getX()- (width/2);
                m1y0 = m1.getY();
                //---
                m1x1 = m1x0 + width;
                m1y1 = m1y0 - height;

                if(isMotorOutOfFrame(m1x0, m1y0, m1x1, m1y1)){
                    p2Won = true;
                    outOfFrame = true;
                    break;
                }

                // any colored pixel that is inside the rectangle of (m1x0,m1y0)-(m1x1,m1y1) is considered a collision
                // in this case, we will go from the top left end of the motorcycle, since it is moving to the top and collisions are more likely to happen from the top side, rather from the bottom
                for(int m = m1y1 + MotorModelOffsetVertical; m < m1y0; m++){
                    for(int n = m1x0; n < m1x1; n++){
                        if(canvas.getPathToMotors(n, m) != 0){
                            won = true;
                            break;
                        }
                    }
                    if(won) break;
                }
                if(won){
                    p2Won = true;
                }
                break;
            case 180:
                width = UniversalData.getMotorHeight();
                height = UniversalData.getMotorWidth();
                // based on the following line, we calculate the motor's drawn dimensions, and check if any colored pixel is inside of these dimensions
                // g.drawImage(motor1, m1Pos.getX(), m1Pos.getY()+(width/4), -width, -height, this);

                m1x0 = m1.getX();
                m1y0 = m1.getY() + (width/4);
                //---
                m1x1 = m1x0 - width;
                m1y1 = m1y0 - height;

                if(isMotorOutOfFrame(m1x0, m1y0, m1x1, m1y1)){
                    p2Won = true;
                    outOfFrame = true;
                    break;
                }

                // any colored pixel that is inside the rectangle of (m1x0,m1y0)-(m1x1,m1y1) is considered a collision
                // in this case, we will go from the top left end of the motorcycle, since it is moving to the left and collisions are more likely to happen from the left side, rather from the right
                for(int n = m1x1 + MotorModelOffsetHorizontal-3; n < m1x0; n++){
                    for(int m = m1y1; m < m1y0; m++){
                        if(canvas.getPathToMotors(n, m) != 0){
                            won = true;
                            break;
                        }
                    }
                    if(won) break;
                }
                if(won){
                    p2Won = true;
                }
                break;
            case 270:
                width = UniversalData.getMotorWidth();
                height = UniversalData.getMotorHeight();
                // based on the following line, we calculate the motor's drawn dimensions, and check if any colored pixel is inside of these dimensions
                // g.drawImage(motor1, m1Pos.getX()-(width/2), m1Pos.getY(), width, height, this);


                m1x0 = m1.getX() - (width/2);
                m1y0 = m1.getY();
                //---
                m1x1 = m1x0 + width;
                m1y1 = m1y0 + height;

                if(isMotorOutOfFrame(m1x0, m1y0, m1x1, m1y1)){
                    p2Won = true;
                    outOfFrame = true;
                    break;
                }


                // any colored pixel that is inside the rectangle of (m1x0,m1y0)-(m1x1,m1y1) is considered a collision
                // in this case, we will go from the bottom left end of the motorcycle, since it is moving to the bottom and collisions are more likely to happen from the bottom left side, rather from the right
                for(int m = m1y1 - MotorModelOffsetVertical; m > m1y0; m--){
                    for(int n = m1x0; n < m1x1; n++){
                        if(canvas.getPathToMotors(n, m) != 0){
                            won = true;
                            break;
                        }
                    }
                    if(won) break;
                }
                if(won){
                    p2Won = true;
                }
                break;
            default:
                m1x0 = m1x1 = m1y0 = m1y1 = 0;
        }



        //MOTOR2
        won = false;
        switch (m2.getFacing()){
            case 0:

                width = UniversalData.getMotorHeight();
                height = UniversalData.getMotorWidth();
                // based on the following line, we calculate the motor's drawn dimensions, and check if any colored pixel is inside of these dimensions
                // g.drawImage(motor1, m1Pos.getX(), m1Pos.getY() + (width/4), width, -height, this);
                m2x0 = m2.getX();
                m2y0 = m2.getY() + (width/4);
                //---
                m2x1 = m2x0 + width;
                m2y1 = m2y0 - height;

                if(isMotorOutOfFrame(m2x0, m2y0, m2x1, m2y1)){
                    p1Won = true;
                    outOfFrame = true;
                    break;
                }

                // any colored pixel that is inside the rectangle of (m1x0,m1y0)-(m1x1,m1y1) is considered a collision
                // in this case, we will go from the top right end of the motorcycle, since it is moving to the right and collisions are more likely to happen from the right side, rather from the left

                for(int n = m2x1 - MotorModelOffsetHorizontal; n > m2x0; n--){
                    for(int m = m2y0; m > m2y1; m--){
                        if(canvas.getPathToMotors(n, m) != 0){
                            won = true;
                            break;
                        }
                    }
                    if(won) break;
                }
                if(won){
                    p1Won = true;
                }

                break;
            case 90:
                width = UniversalData.getMotorWidth();
                height = UniversalData.getMotorHeight();
                // based on the following line, we calculate the motor's drawn dimensions, and check if any colored pixel is inside of these dimensions
                // g.drawImage(motor1, m1Pos.getX()-(width/2), m1Pos.getY(), width, -height, this);

                m2x0 = m2.getX()- (width/2);
                m2y0 = m2.getY();
                //---
                m2x1 = m2x0 + width;
                m2y1 = m2y0 - height;

                if(isMotorOutOfFrame(m2x0, m2y0, m2x1, m2y1)){
                    p1Won = true;
                    outOfFrame = true;
                    break;
                }

                // any colored pixel that is inside the rectangle of (m1x0,m1y0)-(m1x1,m1y1) is considered a collision
                // in this case, we will go from the top left end of the motorcycle, since it is moving to the top and collisions are more likely to happen from the top side, rather from the bottom
                for(int m = m2y1 + MotorModelOffsetVertical; m < m2y0; m++){
                    for(int n = m2x0; n < m2x1; n++){
                        if(canvas.getPathToMotors(n, m) != 0){
                            won = true;
                            break;
                        }
                    }
                    if(won) break;
                }
                if(won){
                    p1Won = true;
                }
                break;
            case 180:
                width = UniversalData.getMotorHeight();
                height = UniversalData.getMotorWidth();
                // based on the following line, we calculate the motor's drawn dimensions, and check if any colored pixel is inside of these dimensions
                // g.drawImage(motor1, m1Pos.getX(), m1Pos.getY()+(width/4), -width, -height, this);

                m2x0 = m2.getX();
                m2y0 = m2.getY() + (width/4);
                //---
                m2x1 = m2x0 - width;
                m2y1 = m2y0 - height;

                if(isMotorOutOfFrame(m2x0, m2y0, m2x1, m2y1)){
                    p1Won = true;
                    outOfFrame = true;
                    break;
                }

                // any colored pixel that is inside the rectangle of (m1x0,m1y0)-(m1x1,m1y1) is considered a collision
                // in this case, we will go from the top left end of the motorcycle, since it is moving to the left and collisions are more likely to happen from the left side, rather from the right
                for(int n = m2x1 + MotorModelOffsetHorizontal-3; n < m2x0; n++){
                    for(int m = m2y1; m < m2y0; m++){
                        if(canvas.getPathToMotors(n, m) != 0){
                            won = true;
                            break;
                        }
                    }
                    if(won) break;
                }
                if(won){
                    p1Won = true;
                }
                break;
            case 270:
                width = UniversalData.getMotorWidth();
                height = UniversalData.getMotorHeight();
                // based on the following line, we calculate the motor's drawn dimensions, and check if any colored pixel is inside of these dimensions
                // g.drawImage(motor1, m1Pos.getX()-(width/2), m1Pos.getY(), width, height, this);


                m2x0 = m2.getX() - (width/2);
                m2y0 = m2.getY();
                //---
                m2x1 = m2x0 + width;
                m2y1 = m2y0 + height;

                if(isMotorOutOfFrame(m2x0, m2y0, m2x1, m2y1)){
                    p1Won = true;
                    outOfFrame = true;
                    break;
                }


                // any colored pixel that is inside the rectangle of (m1x0,m1y0)-(m1x1,m1y1) is considered a collision
                // in this case, we will go from the bottom left end of the motorcycle, since it is moving to the bottom and collisions are more likely to happen from the bottom left side, rather from the right
                for(int m = m2y1 - MotorModelOffsetVertical; m > m2y0; m--){
                    for(int n = m2x0; n < m2x1; n++){
                        if(canvas.getPathToMotors(n, m) != 0){
                            won = true;
                            break;
                        }
                    }
                    if(won) break;
                }
                if(won){
                    p1Won = true;
                }
                break;
            default:
                m2x0 = m2x1 = m2y0 = m2y1 = 0;
        }


        if(!outOfFrame){
            draw = areMotorsCollided(m1x0, m1x1, m1y0, m1y1, m2x0, m2x1, m2y0, m2y1);
        }


        if ((p1Won && p2Won) || draw) {
            // a scenario where they both crossed eachothers line in the same gamecycle, or both is out of frame at the same time; means draw
            // or simply their coordinates are the same, meaning they collided the same time as motors, means draw
            return 3;
        }
        if (p1Won) {
            return 1;
        } else if (p2Won) {
            return 2;
        }

        return 0;
    }
    private boolean isMotorOutOfFrame(int x0, int y0, int x1, int y1){
        return (x0 < 0 || x0 >= UniversalData.getWindowDimension().width || x1 < 0 || x1 >= UniversalData.getWindowDimension().width || y0 < 0 || y0 >= UniversalData.getWindowDimension().height || y1 < 0 || y1 >= UniversalData.getWindowDimension().height);
    }
    private boolean areMotorsCollided(int m1x0, int m1x1, int m1y0, int m1y1, int m2x0, int m2x1, int m2y0, int m2y1){
        // base motor collide checker
        boolean collided = false;
        int bm1x0 = Math.min(m1x0, m1x1) + MotorModelOffsetHorizontal;
        int bm1x1 = Math.max(m1x0, m1x1) - MotorModelOffsetHorizontal;
        int bm1y0 = Math.min(m1y0, m1y1) + MotorModelOffsetVertical;
        int bm1y1 = Math.max(m1y0, m1y1) - MotorModelOffsetVertical;

        int bm2x0 = Math.min(m2x0, m2x1) + MotorModelOffsetHorizontal;
        int bm2x1 = Math.max(m2x0, m2x1) - MotorModelOffsetHorizontal;
        int bm2y0 = Math.min(m2y0, m2y1) + MotorModelOffsetVertical;
        int bm2y1 = Math.max(m2y0, m2y1) - MotorModelOffsetVertical;
        // motor 1 loop
        for(int n1 = bm1x0; n1 < bm1x1; n1++){
            // only check the boundaries, no need to check the inner motor
            for(int m1 = bm1y0; m1 < bm1y1; m1++){
                // motor2 loop
                if(n1 != bm1x0 && n1 != (bm1x1-1) && m1 > bm1y0 && m1 < (bm1y1-1)) continue;
                for(int n2 = bm2x0; n2 < bm2x1; n2++){
                    for(int m2 = bm2y0; m2 < bm2y1; m2++){
                        if(n2 != bm2x0 && n2 != (bm2x1-1) && m2 > bm2y0 && m2 < (bm2y1-1)) continue;
                        if(n1 == n2 && m1 == m2){
                            collided = true;
                            break;
                        }
                        if(collided) break;
                    }
                }
                if(collided) break;
            }
            if(collided) break;
        }
        return collided;
    }
    public void handleKeyPress(int keycode) {
        switch (keycode) {
            case Keys.A:
                //motor1, facing: left (180)
                if (m1.getFacing() == 0) return;
                m1.setFacing(180);
                break;
            case Keys.W:
                //motor1, facing: up (90)
                if (m1.getFacing() == 270) return;
                m1.setFacing(90);

                break;
            case Keys.D:
                //motor1, facing: right (0)
                if (m1.getFacing() == 180) return;
                m1.setFacing(0);
                break;
            case Keys.S:
                //motor1, facing: down (270)
                if (m1.getFacing() == 90) return;
                m1.setFacing(270);
                break;
            case Keys.LeftArrow:
                //motor2, facing: left (180)
                if (m2.getFacing() == 0) return;
                m2.setFacing(180);
                break;
            case Keys.UpArrow:
                //motor2, facing: up (90)
                if (m2.getFacing() == 270) return;
                m2.setFacing(90);
                break;
            case Keys.RightArrow:
                //motor2, facing: right (0)
                if (m2.getFacing() == 180) return;
                m2.setFacing(0);
                break;
            case Keys.DownArrow:
                //motor2, facing: down (270)
                if (m2.getFacing() == 90) return;
                m2.setFacing(270);
                break;
            case Keys.NewGame:
                // new game has to launch
                if(isGameGoing){
                    log("Back to menu button pressed, stopping game and returning to menu..", false);
                    launchGame(p1Name, p2Name, pOne__color, pTwo__color);
                }
                break;
            case Keys.BackToMenu:
                // return back to the menu
                if(isGameGoing) {
                    log("New game button pressed, stopping game and starting a new one..", false);
                    isGameGoing = false;
                    gui.generateLogin(false, false);
                }
                break;
            default:
                log("No matching keycode was found for the sent code, exiting..", true);
                return;
        }
    }

    public List<Score> getScoreBoardData(){
        return db.getScores();
    }

    private void log(String msg, boolean error) {
        if (error) {
            System.err.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][GAME][ERROR] " + msg);
        } else {
            System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][GAME] " + msg);
        }
    }

    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
}