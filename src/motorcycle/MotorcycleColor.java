package motorcycle;

import java.awt.*;

public enum MotorcycleColor {
    YELLOW(new Color(182, 122, 43)),
    BLUE(new Color(110, 240, 244)),
    GREEN(new Color(63, 181, 36));
    public final Color color;
    private MotorcycleColor(Color color){
        this.color = color;
    }
}
