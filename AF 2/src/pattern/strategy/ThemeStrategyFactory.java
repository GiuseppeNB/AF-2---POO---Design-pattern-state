package src.pattern.strategy;

import java.util.HashMap;
import java.util.Map;

public class ThemeStrategyFactory {
    private static final Map<String, ThemeStrategy> THEMES = new HashMap<>();

    static {
        THEMES.put("Claro", new LightThemeStrategy());
        THEMES.put("Escuro", new DarkThemeStrategy());
        THEMES.put("Azul", new BlueThemeStrategy());
    }

    public static ThemeStrategy getTheme(String themeName) {
        ThemeStrategy theme = THEMES.get(themeName);
        return theme != null ? theme : new LightThemeStrategy();
    }
}