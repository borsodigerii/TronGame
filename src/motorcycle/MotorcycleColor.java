package motorcycle;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public enum MotorcycleColor {
    YELLOW("Yellow", new Color(182, 122, 43)),
    BLUE("Blue", new Color(110, 240, 244)),
    GREEN("Green", new Color(63, 181, 36)),
    RED("Red", new Color(224, 36, 36));

    /**
     * A statical collection of the available enum values
     */
    public static final MotorcycleColor[] values = MotorcycleColor.class.getEnumConstants();


    /**
     * Returns the MotorcycleColor instance based on the provided color-name
     * @param name The color's name to return
     * @return MotorcycleColor instance if color found, null otherwise
     */
    public static MotorcycleColor getColorByName(String name){
        return (MotorcycleColor) Arrays.stream(values).filter((color) -> Objects.equals(color.displayName, name)).toArray()[0];
    }


    /**
     * Color property
     */
    public final Color color;


    /**
     * Name property
     */
    public final String displayName;


    /**
     * Used internally only, used to create a color
     * @param displayName The name of the color
     * @param color The color
     */
    private MotorcycleColor(String displayName, Color color){
        this.displayName = displayName;
        this.color = color;
    }
}
