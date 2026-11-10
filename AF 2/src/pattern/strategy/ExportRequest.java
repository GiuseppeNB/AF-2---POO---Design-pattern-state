package pattern.strategy;

public class ExportRequest {
    private final String formatName;
    private final boolean includeHeader;
    private final boolean includeWatermark;
    private final boolean includeBorder;

    public ExportRequest(String formatName, boolean includeHeader, boolean includeWatermark, boolean includeBorder) {
        this.formatName = formatName;
        this.includeHeader = includeHeader;
        this.includeWatermark = includeWatermark;
        this.includeBorder = includeBorder;
    }

    public String getFormatName() { return formatName; }
    public boolean isIncludeHeader() { return includeHeader; }
    public boolean isIncludeWatermark() { return includeWatermark; }
    public boolean isIncludeBorder() { return includeBorder; }
}