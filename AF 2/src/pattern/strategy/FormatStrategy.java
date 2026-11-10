package src.pattern.strategy;

public interface FormatStrategy {
    String format(String rawText);
    String getExtension();
}