package model;

import pattern.state.DocumentState;
import pattern.state.EditingState;
import pattern.strategy.DefaultExportStrategy;
import pattern.strategy.ExportRequest;
import pattern.strategy.ExportResult;
import pattern.strategy.ExportStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DocumentContext {
    private DocumentState currentState;
    private String content;
    private ExportStrategy exportStrategy;
    private ExportResult lastExportResult;
    private String currentStatus;
    private final List<DocumentContextListener> listeners = new ArrayList<>();

    public DocumentContext(String initialContent) {
        this.content = initialContent;
        this.currentState = new EditingState();
        this.exportStrategy = new DefaultExportStrategy();
        this.currentStatus = " Status: Pronto";
    }

    public void changeState(DocumentState newState) {
        this.currentState = newState;
        log("Estado alterado para: " + newState.getStateName());
        notifyStateChanged();
    }

    public void editContent(String newContent) {
        currentState.onEditContent(this, newContent);
    }

    public void exportDocument(ExportRequest request) {
        currentState.onExport(this, request);
    }

    public void resetDocument() {
        currentState.onReset(this);
    }

    public void setContentDirectly(String content) {
        this.content = content;
        notifyContentChanged();
    }

    public String getContent() {
        return content;
    }

    public DocumentState getCurrentState() {
        return currentState;
    }

    public ExportStrategy getExportStrategy() {
        return exportStrategy;
    }

    public void setExportStrategy(ExportStrategy exportStrategy) {
        this.exportStrategy = exportStrategy;
    }

    public ExportResult getLastExportResult() {
        return lastExportResult;
    }

    public void setLastExportResult(ExportResult lastExportResult) {
        this.lastExportResult = lastExportResult;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setStatus(String status) {
        this.currentStatus = status;
        notifyStatusChanged();
    }

    public void log(String message) {
        String timestampedMsg = "[" + LocalDateTime.now().toLocalTime().toString().substring(0, 8) + "] " + message;
        notifyLogAdded(timestampedMsg);
    }

    public void addListener(DocumentContextListener listener) {
        listeners.add(listener);
    }

    private void notifyStateChanged() {
        for (DocumentContextListener listener : listeners) {
            listener.onStateChanged(currentState);
        }
    }

    private void notifyContentChanged() {
        for (DocumentContextListener listener : listeners) {
            listener.onContentChanged(content);
        }
    }

    private void notifyStatusChanged() {
        for (DocumentContextListener listener : listeners) {
            listener.onStatusChanged(currentStatus);
        }
    }

    private void notifyLogAdded(String message) {
        for (DocumentContextListener listener : listeners) {
            listener.onLogAdded(message);
        }
    }
}