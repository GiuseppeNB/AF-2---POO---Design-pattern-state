package src.pattern.strategy;

public interface ExportStrategy {
    ExportResult export(ExportRequest request, String rawText);
}