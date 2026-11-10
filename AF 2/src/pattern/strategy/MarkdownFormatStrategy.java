package pattern.strategy;

public class MarkdownFormatStrategy implements FormatStrategy {
    @Override
    public String format(String rawText) {
        return "# Relatório\n\n" + rawText + "\n";
    }

    @Override
    public String getExtension() {
        return "md";
    }
}