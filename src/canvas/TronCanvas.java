package canvas;

import motorcycle.MotorCyclePosition;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class TronCanvas extends Canvas {
    private BufferedImage motor1;
    private BufferedImage motor2;
    public void paint(Graphics g){
        drawMotors(g, null, null);
    }


    private void drawMotors(Graphics g, MotorCyclePosition m1Pos, MotorCyclePosition m2Pos){
        if(motor1 == null || motor2 == null){
            // trying to load motor assets if they haven't been loaded before
            try {
                //BufferedImage motor1 = ImageIO.read(new File("tronBikeBlue.png"));
                motor1 = rotateClockwise90(ImageIO.read(getClass().getResource("/tronBikeBlue.png")));
                motor2 = rotateClockwise90(ImageIO.read(getClass().getResource("/tronBikeYellow.png")));
            } catch (IOException | IllegalArgumentException ex) {
                log("Could not access the bike's 2d assets.", true);
            }
        }

        int motor1x = 50;
        int motor1y = getHeight() - 200;
        if(m1Pos == null){
            g.drawImage(motor1, motor1x, motor1y, 50, 100, this);
        }else{
            g.drawImage(motor1, motor1x, motor1y, m1Pos.getX(), m1Pos.getY(), this);
        }


        int motor2x = getWidth() - 100;
        int motor2y = 50;
        if(m2Pos == null){
            g.drawImage(motor2, motor2x, motor2y, 50, 100, this);
        }else{
            g.drawImage(motor2, motor2x, motor2y, m2Pos.getX(), m2Pos.getY(), this);
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
    public static BufferedImage rotateAntiClockwise90(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();

        BufferedImage dest = new BufferedImage(height, width, src.getType());

        Graphics2D graphics2D = dest.createGraphics();
        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate(Math.PI, height / 2, width / 2);
        graphics2D.drawRenderedImage(src, null);

        return dest;
    }
}
