package pattern.strategy;

import java.awt.Color;

public class DarkThemeStrategy implements ThemeStrategy {
    @Override
    public String getThemeName() { return "Escuro"; }

    @Override
    public ThemeColors getColors() {
        return new ThemeColors(
            new Color(40, 44, 52),
            new Color(171, 178, 191),
            new Color(30, 33, 39),
            new Color(150, 160, 175)
        );
    }
}