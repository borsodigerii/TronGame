package canvas;

import utils.UniversalData;

import javax.swing.*;
import java.awt.*;

public class TronWin extends JPanel {

    /**
     * The printed winning player's name
     */
    private String winningPlayer = "";


    /**
     * Flag to indicate if the game ended with a draw
     */
    private boolean isDraw = false;


    public void paintComponent(Graphics g){
        g.setColor(new Color(0, 0, 0, 113));
        g.fillRect(0, 0, UniversalData.getWindowDimension().width, UniversalData.getWindowDimension().height);
        g.setColor(new Color(255, 255, 255));

        g.setFont(new Font("Arial", Font.BOLD, 60));
        FontMetrics metrics = g.getFontMetrics();
        if(isDraw){
            g.drawString("A játék döntetlen!", (UniversalData.getWindowDimension().width /2) - (metrics.stringWidth("A játék döntetlen!") / 2), UniversalData.getWindowDimension().height/2 - 100);
        }else{
            g.drawString(String.valueOf(this.winningPlayer) + " nyert!", (UniversalData.getWindowDimension().width /2) - (metrics.stringWidth(String.valueOf(this.winningPlayer) + " nyert!") / 2), UniversalData.getWindowDimension().height/2 - 100);
        }
        g.setFont(new Font("Arial", Font.BOLD, 20));
        metrics = g.getFontMetrics();
        g.drawString("(N) - új játék, (M) - vissza a menübe", (UniversalData.getWindowDimension().width / 2) - (metrics.stringWidth("(N) - új játék, (M) - vissza a menübe") / 2), (UniversalData.getWindowDimension().height / 2) + 30);

    }

    public void setWinningPlayer(String winningPlayer) {
        this.winningPlayer = winningPlayer;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }
}
