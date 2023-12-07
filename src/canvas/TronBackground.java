package canvas;

import utils.UniversalData;

import javax.swing.*;
import java.awt.*;

public class TronBackground extends JPanel {
    public void paintComponent(Graphics g){
        int windowWidth = UniversalData.getWindowDimension().width;
        int windowHeight = UniversalData.getWindowDimension().height;
        g.fillRect(0, 0, windowWidth, windowHeight);
        for(int i = 0; i <= windowWidth; i+=50){
            g.setColor(new Color(100, 100, 100, 140));
            g.fillRect(i, 0, 2, windowHeight);
        }
        for(int i = 0; i <= windowHeight; i+=50){
            g.setColor(new Color(100, 100, 100, 140));
            g.fillRect(0, i, windowWidth, 2);
        }
    }
}
