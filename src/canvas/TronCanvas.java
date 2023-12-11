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
    private BufferedImage motor1;
    private BufferedImage motor2;

    private int[][] motorPath = new int[UniversalData.getWindowDimension().height][UniversalData.getWindowDimension().width];

    public void addPathToMotors(int motor, int x, int y){
        motorPath[x][y] = motor;
    }
    public int getPathToMotors(int x, int y){
        return motorPath[x][y];
    }
    public void resetMotorPath(){
        motorPath = new int[UniversalData.getWindowDimension().height][UniversalData.getWindowDimension().width];
    }

    public void paintComponent(Graphics g){
        drawMotors(g, TronGame.m1, TronGame.m2);
        drawPaths(g);
    }


    private void drawMotors(Graphics g, MotorCyclePosition m1Pos, MotorCyclePosition m2Pos) {
        try{
            int width, height;
            switch (m1Pos.getFacing()){
                case 0:
                    width = UniversalData.getMotorHeight();
                    height = UniversalData.getMotorWidth();
                    motor1 = rotateClockwise90(rotateClockwise90(ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeBlue.png")))));
                    g.drawImage(motor1, m1Pos.getX(), m1Pos.getY() + (width/4), width, -height, this);
                    break;
                case 90:
                    width = UniversalData.getMotorWidth();
                    height = UniversalData.getMotorHeight();

                    motor1 = rotateClockwise90(ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeBlue.png"))));
                    g.drawImage(motor1, m1Pos.getX()-(width/2) + 4, m1Pos.getY(), width, -height, this);
                    break;
                case 180:
                    width = UniversalData.getMotorHeight();
                    height = UniversalData.getMotorWidth();
                    motor1 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeBlue.png")));
                    g.drawImage(motor1, m1Pos.getX(), m1Pos.getY()+(width/4), -width, -height, this);
                    break;
                case 270:
                    width = UniversalData.getMotorWidth();
                    height = UniversalData.getMotorHeight();
                    motor1 = rotateClockwise90(rotateClockwise90(rotateClockwise90(ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeBlue.png"))))));
                    g.drawImage(motor1, m1Pos.getX()-(width/2), m1Pos.getY(), width, height, this);
                    break;
            }



            switch (m2Pos.getFacing()){
                case 0:
                    width = UniversalData.getMotorHeight();
                    height = UniversalData.getMotorWidth();
                    motor2 = rotateClockwise90(rotateClockwise90(ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeYellow.png")))));
                    g.drawImage(motor2, m2Pos.getX(), m2Pos.getY() + (width/4), width, -height, this);
                    break;
                case 90:
                    width = UniversalData.getMotorWidth();
                    height = UniversalData.getMotorHeight();

                    motor2 = rotateClockwise90(ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeYellow.png"))));
                    g.drawImage(motor2, m2Pos.getX()-(width/2) + 4, m2Pos.getY(), width, -height, this);
                    break;
                case 180:
                    width = UniversalData.getMotorHeight();
                    height = UniversalData.getMotorWidth();
                    motor2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeYellow.png")));
                    g.drawImage(motor2, m2Pos.getX(), m2Pos.getY()+(width/4), -width, -height, this);
                    break;
                case 270:
                    width = UniversalData.getMotorWidth();
                    height = UniversalData.getMotorHeight();
                    motor2 = rotateClockwise90(rotateClockwise90(rotateClockwise90(ImageIO.read(Objects.requireNonNull(getClass().getResource("/tronBikeYellow.png"))))));
                    g.drawImage(motor2, m2Pos.getX()-(width/2), m2Pos.getY(), width, height, this);
                    break;
            }
        }catch (IOException e){
            log("Could not open motor models for reading.", true);
        }


    }

    private void drawPaths(Graphics g){
        for(int i = 0; i < UniversalData.getWindowDimension().height; i++){
            for(int f = 0; f < UniversalData.getWindowDimension().width; f++){
                if(motorPath[i][f] == 1){
                    g.setColor(TronGame.pOne__color.color);
                    g.fillRect(i, f, 4, 4);
                }else if(motorPath[i][f] == 2){
                    g.setColor(TronGame.pTwo__color.color);
                    g.fillRect(i, f, 4, 4);
                }
            }
        }
    }

    private void log(String msg, boolean error){
        if(error){
            System.err.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][CANVAS][ERROR] " + msg);
        }else{
            System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()) + "][CANVAS] " + msg);
        }

    }

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

}
