package motorcycle;

import java.awt.*;

public enum MotorcycleColor {
    RED(new Color(235, 54, 54)),
    BLUE(new Color(42, 49, 235)),
    GREEN(new Color(63, 181, 36));
    public final Color color;
    private MotorcycleColor(Color color){
        this.color = color;
    }
}
