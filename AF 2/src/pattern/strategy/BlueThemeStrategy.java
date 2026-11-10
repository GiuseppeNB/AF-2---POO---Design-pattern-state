package pattern.strategy;

import java.awt.Color;

public class BlueThemeStrategy implements ThemeStrategy {
    @Override
    public String getThemeName() { return "Azul"; }

    @Override
    public ThemeColors getColors() {
        return new ThemeColors(
            new Color(230, 240, 250),
            new Color(10, 30, 80),
            new Color(210, 225, 245),
            new Color(5, 20, 60)
        );
    }
}