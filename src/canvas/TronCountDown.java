package canvas;

import utils.UniversalData;

import javax.swing.*;
import java.awt.*;

public class TronCountDown extends JPanel {
    /**
     * The countdown number
     */
    public int count = 3;

    public void setCount(int count) {
        this.count = count;
    }

    public void paintComponent(Graphics g){
        g.setColor(new Color(0, 0, 0, 113));
        g.fillRect(0, 0, UniversalData.getWindowDimension().width, UniversalData.getWindowDimension().height);
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 100));
        g.drawString(String.valueOf(this.count), UniversalData.getWindowDimension().width /2 - 30, UniversalData.getWindowDimension().height/2);
    }
}
