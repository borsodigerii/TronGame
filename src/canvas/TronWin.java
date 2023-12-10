package canvas;

import utils.UniversalData;

import javax.swing.*;
import java.awt.*;

public class TronWin extends JPanel {
    private String winningPlayer = "";
    private boolean isDraw = false;
    public void paintComponent(Graphics g){
        g.setColor(new Color(0, 0, 0, 113));
        g.fillRect(0, 0, UniversalData.getWindowDimension().width, UniversalData.getWindowDimension().height);
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 100));
        if(isDraw){
            g.drawString("A játék döntetlen!", (UniversalData.getWindowDimension().width /2) - (getWidth() / 2), UniversalData.getWindowDimension().height/2);
        }else{
            g.drawString(String.valueOf(this.winningPlayer) + " nyert!", (UniversalData.getWindowDimension().width /2) - (getWidth() / 2), UniversalData.getWindowDimension().height/2);
        }

    }

    public void setWinningPlayer(String winningPlayer) {
        this.winningPlayer = winningPlayer;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }
}
