package canvas;

import game.TronGame;
import motorcycle.MotorCyclePosition;
import utils.UniversalData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class TronCanvas extends JPanel {

    /**
     * The image buffer for the first motor
     */
    private BufferedImage motor1;


    /**
     * The image buffer for the second motor
     */
    private BufferedImage motor2;


    /**
     * A matrix which keeps track of the light-paths the motorcycles emit from themselves
     */
    private int[][] motorPath = new int[UniversalData.getWindowDimension().height][UniversalData.getWindowDimension().width];


    /**
     * Adds a light-path to the system on the specified coordinate with the given motor's color
     * @param motor The motor number to add it as (can be either 1 or 2)
     * @param x The x coordinate for the light-path
     * @param y The y coordinate for the light-path
     */
    public void addPathToMotors(int motor, int x, int y){
        motorPath[x][y] = motor;
    }


    /**
     * Returns the light-path's owner for the specified coordinate
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The number of the owner's motor (1 or 2), 0 if there is no light-path on the specified coordinate
     */
    public int getPathToMotors(int x, int y){
        return motorPath[x][y];
    }


    /**
     * Resets the motor path matrix
     */
    public void resetMotorPath(){
        motorPath = new int[UniversalData.getWindowDimension().height][UniversalData.getWindowDimension().width];
    }


    public void paintComponent(Graphics g){
        drawMotors(g, TronGame.m1, TronGame.m2);
        drawPaths(g);
    }


    /**
     * Draws the motorcycles' sprites to the canvas based on the provided coordinates
     * @param g The Graphics context
     * @param m1Pos Position for motor nr.1
     * @param m2Pos Position for motor nr.2
     */
    private void drawMotors(Graphics g, MotorCyclePosition m1Pos, MotorCyclePosition m2Pos) {
        try{
            int width, height, width2, height2;
            // cos(0) = 1  ----  0deg
            // sin(0) = 0
            //---
            // cos(pi/2) = 0 --  90deg
            // sin(pi/2) = 1

            switch (m1Pos.getFacing()){
                case 0:
                    width = UniversalData.getMotorHeight();
                    height = UniversalData.getMotorWidth();
                    width2 = width;
                    height2 = -1 * height;
                    motor1 = rotateClockwise90(rotateClockwise90(ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeBlue.png")))));
                    //g.drawImage(motor1, m1Pos.getX(), m1Pos.getY() + (height/2), width, -height, this);
                    break;
                case 90:
                    width = UniversalData.getMotorWidth();
                    height = UniversalData.getMotorHeight();
                    width2 = width;
                    height2 = height * -1;
                    motor1 = rotateClockwise90(ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeBlue.png"))));
                    //g.drawImage(motor1, m1Pos.getX()-(width/2) + 4, m1Pos.getY(), width, -height, this);
                    break;
                case 180:
                    width = UniversalData.getMotorHeight();
                    height = UniversalData.getMotorWidth();
                    width2 = width * -1;
                    height2 = height * -1;
                    motor1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeBlue.png")));
                    //g.drawImage(motor1, m1Pos.getX(), m1Pos.getY()+(height/2), -width, -height, this);
                    break;
                case 270:
                    width = UniversalData.getMotorWidth();
                    height = UniversalData.getMotorHeight();
                    width2 = UniversalData.getMotorWidth();
                    height2 = UniversalData.getMotorHeight();
                    motor1 = rotateClockwise90(rotateClockwise90(rotateClockwise90(ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeBlue.png"))))));
                    //g.drawImage(motor1, m1Pos.getX()-(width/2), m1Pos.getY(), width, height, this);
                    break;
                default:
                    width = UniversalData.getMotorWidth();
                    height = UniversalData.getMotorHeight();
                    width2 = UniversalData.getMotorWidth();
                    height2 = UniversalData.getMotorHeight();
                    break;
            }
            g.drawImage(motor1, m1Pos.getX() - ((int)Math.abs(Math.sin(Math.toRadians(m1Pos.getFacing()))) * (width/2)), m1Pos.getY() + ((int)Math.abs(Math.cos(Math.toRadians(m1Pos.getFacing()))) * (height/2)), width2, height2, this);


            switch (m2Pos.getFacing()){
                case 0:
                    width = UniversalData.getMotorHeight();
                    height = UniversalData.getMotorWidth();
                    width2 = width;
                    height2 = -1 * height;
                    motor2 = rotateClockwise90(rotateClockwise90(ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeYellow.png")))));
                    //g.drawImage(motor2, m2Pos.getX(), m2Pos.getY() + (height/2), width, -height, this);
                    break;
                case 90:
                    width = UniversalData.getMotorWidth();
                    height = UniversalData.getMotorHeight();
                    width2 = width;
                    height2 = height * -1;
                    motor2 = rotateClockwise90(ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeYellow.png"))));
                    //g.drawImage(motor2, m2Pos.getX()-(width/2) + 4, m2Pos.getY(), width, -height, this);
                    break;
                case 180:
                    width = UniversalData.getMotorHeight();
                    height = UniversalData.getMotorWidth();
                    width2 = width * -1;
                    height2 = height * -1;
                    motor2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeYellow.png")));
                    //g.drawImage(motor2, m2Pos.getX(), m2Pos.getY()+(height/2), -width, -height, this);
                    break;
                case 270:
                    width = UniversalData.getMotorWidth();
                    height = UniversalData.getMotorHeight();
                    width2 = UniversalData.getMotorWidth();
                    height2 = UniversalData.getMotorHeight();
                    motor2 = rotateClockwise90(rotateClockwise90(rotateClockwise90(ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeYellow.png"))))));
                    //g.drawImage(motor2, m2Pos.getX()-(width/2), m2Pos.getY(), width, height, this);
                    break;
                default:
                    width = UniversalData.getMotorWidth();
                    height = UniversalData.getMotorHeight();
                    width2 = UniversalData.getMotorWidth();
                    height2 = UniversalData.getMotorHeight();
                    break;
            }
            g.drawImage(motor2, m2Pos.getX() - ((int)Math.abs(Math.sin(Math.toRadians(m2Pos.getFacing()))) * (width/2)), m2Pos.getY() + ((int)Math.abs(Math.cos(Math.toRadians(m2Pos.getFacing()))) * (height/2)), width2, height2, this);
        }catch (IOException e){
            log("Could not open motor models for reading.", true);
        }
    }


    /**
     * Draws the light-paths to the canvas
     * @param g The Graphics context
     */
    private void drawPaths(Graphics g){
        for(int i = 0; i < UniversalData.getWindowDimension().height; i++){
            for(int f = 0; f < UniversalData.getWindowDimension().width; f++){
                if(motorPath[i][f] == 1){
                    g.setColor(TronGame.pOne__color.color);
                    g.fillRect(i-2, f-2, 4, 4);
                }else if(motorPath[i][f] == 2){
                    g.setColor(TronGame.pTwo__color.color);
                    g.fillRect(i-2, f-2, 4, 4);
                }
            }
        }
    }


    /**
     * Rotates the given image buffer by 90 degrees visually without touching its image-ratio.
     * @param src The image buffer to rotate
     * @return The rotates image buffer
     */
    public static BufferedImage rotateClockwise90(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();

        BufferedImage dest = new BufferedImage(height, width, src.getType());

        Graphics2D graphics2D = dest.createGraphics();
        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
        graphics2D.drawRenderedImage(src, null);

        return dest;
    }


    /**
     * Logging utility, for easier reporting to the console
     * @param msg The message to log
     * @param error True if it is an error, false otherwise
     */
    private void log(String msg, boolean error){
        if(error){
            System.err.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][CANVAS][ERROR] " + msg);
        }else{
            System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][CANVAS] " + msg);
        }

    }



}
