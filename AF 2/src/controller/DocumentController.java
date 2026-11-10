package src.controller;

import model.DocumentContext;
import pattern.strategy.ExportRequest;

public class DocumentController {
    private final DocumentContext context;

    public DocumentController(DocumentContext context) {
        this.context = context;
    }

    public void handleContentEdit(String newContent) {
        context.editContent(newContent);
    }

    public void handleExport(ExportRequest request) {
        context.exportDocument(request);
    }

    public void handleReset() {
        context.resetDocument();
    }

    public void handleApplyTheme(String themeName) {
        context.log("Alterando tema visual para: " + themeName);
    }
}