package src.pattern.strategy;

public class ExportResult {
    private final boolean success;
    private final String fileName;
    private final String outputContent;
    private final String errorMessage;

    private ExportResult(boolean success, String fileName, String outputContent, String errorMessage) {
        this.success = success;
        this.fileName = fileName;
        this.outputContent = outputContent;
        this.errorMessage = errorMessage;
    }

    public static ExportResult success(String fileName, String outputContent) {
        return new ExportResult(true, fileName, outputContent, null);
    }

    public static ExportResult error(String errorMessage) {
        return new ExportResult(false, null, null, errorMessage);
    }

    public boolean isSuccess() { return success; }
    public String getFileName() { return fileName; }
    public String getOutputContent() { return outputContent; }
    public String getErrorMessage() { return errorMessage; }
}