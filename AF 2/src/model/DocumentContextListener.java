package model;

import pattern.state.DocumentState;

public interface DocumentContextListener {
    void onStateChanged(DocumentState newState);
    void onContentChanged(String newContent);
    void onStatusChanged(String newStatus);
    void onLogAdded(String logMessage);
}