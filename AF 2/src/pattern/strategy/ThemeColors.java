package src.pattern.strategy;

import java.awt.Color;

public class ThemeColors {
    private final Color contentBg;
    private final Color contentFg;
    private final Color logBg;
    private final Color logFg;

    public ThemeColors(Color contentBg, Color contentFg, Color logBg, Color logFg) {
        this.contentBg = contentBg;
        this.contentFg = contentFg;
        this.logBg = logBg;
        this.logFg = logFg;
    }

    public Color getContentBg() { return contentBg; }
    public Color getContentFg() { return contentFg; }
    public Color getLogBg() { return logBg; }
    public Color getLogFg() { return logFg; }
}