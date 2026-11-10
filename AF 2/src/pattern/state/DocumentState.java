package src.pattern.state;

import model.DocumentContext;
import pattern.strategy.ExportRequest;

public interface DocumentState {
    void onEditContent(DocumentContext context, String newContent);
    void onExport(DocumentContext context, ExportRequest request);
    void onReset(DocumentContext context);
    String getStateName();
    boolean canEdit();
}