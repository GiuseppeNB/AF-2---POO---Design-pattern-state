package pattern.strategy;

import java.awt.Color;

public class LightThemeStrategy implements ThemeStrategy {
    @Override
    public String getThemeName() { return "Claro"; }

    @Override
    public ThemeColors getColors() {
        return new ThemeColors(
            Color.WHITE,
            Color.BLACK,
            new Color(245, 245, 245),
            Color.DARK_GRAY
        );
    }
}