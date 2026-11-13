package pattern.strategy;

import java.util.HashMap;
import java.util.Map;

public class FormatStrategyFactory {
    private static final Map<String, FormatStrategy> STRATEGIES = new HashMap<>();

    static {
        STRATEGIES.put("Texto Puro", new PlainTextFormatStrategy());
        STRATEGIES.put("HTML", new HtmlFormatStrategy());
        STRATEGIES.put("Markdown", new MarkdownFormatStrategy());
        STRATEGIES.put("JSON", new JsonFormatStrategy());
    }

    public static FormatStrategy getStrategy(String formatName) {
        FormatStrategy strategy = STRATEGIES.get(formatName);
        return strategy != null ? strategy : new PlainTextFormatStrategy();
    }
}
