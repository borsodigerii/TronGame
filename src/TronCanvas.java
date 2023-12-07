import java.awt.*;

public class TronCanvas extends Canvas {
    public void paint(Graphics g){
        printBackground(g);
    }

    private void printBackground(Graphics g){
        g.fillRect(0, 0, 600, 600);
        for(int i = 0; i <= 600; i+=50){
            g.setColor(new Color(100, 100, 100, 140));
            g.fillRect(i, 0, 2, 600);
        }
        for(int i = 0; i <= 600; i+=50){
            g.setColor(new Color(100, 100, 100, 140));
            g.fillRect(0, i, 600, 2);
        }
    }
}
