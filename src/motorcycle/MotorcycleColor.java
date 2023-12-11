package motorcycle;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public enum MotorcycleColor {
    YELLOW("Yellow", new Color(182, 122, 43)),
    BLUE("Blue", new Color(110, 240, 244)),
    GREEN("Green", new Color(63, 181, 36)),
    RED("Red", new Color(224, 36, 36));

    public static final MotorcycleColor[] values = MotorcycleColor.class.getEnumConstants();
    public static MotorcycleColor getColorByName(String name){
        return (MotorcycleColor) Arrays.stream(values).filter((color) -> Objects.equals(color.displayName, name)).toArray()[0];
    }
    public final Color color;
    public final String displayName;
    private MotorcycleColor(String displayName, Color color){
        this.displayName = displayName;
        this.color = color;
    }
}
