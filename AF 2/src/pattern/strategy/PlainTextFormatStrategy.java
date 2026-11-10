package pattern.strategy;

public class PlainTextFormatStrategy implements FormatStrategy {
    @Override
    public String format(String rawText) {
        return rawText + "\n";
    }

    @Override
    public String getExtension() {
        return "txt";
    }
}