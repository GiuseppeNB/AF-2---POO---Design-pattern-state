package src.view;

import controller.DocumentController;
import model.DocumentContext;
import model.DocumentContextListener;
import pattern.state.DocumentState;
import pattern.strategy.ExportRequest;
import pattern.strategy.ExportResult;
import pattern.strategy.ThemeColors;
import pattern.strategy.ThemeStrategy;
import pattern.strategy.ThemeStrategyFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class DocumentView extends JFrame implements DocumentContextListener {

    private JTextArea contentArea;
    private JComboBox<String> formatSelector;
    private JComboBox<String> themeSelector;
    private JCheckBox headerCheckBox;
    private JCheckBox watermarkCheckBox;
    private JCheckBox borderCheckBox;
    private JTextArea logArea;
    private JLabel statusLabel;
    private JLabel stateLabel;

    private final DocumentContext context;
    private final DocumentController controller;
    private boolean updatingFromModel = false;

    public DocumentView() {
        super("Processador de Documentos e Relatórios - Arquitetura Refatorada (State Pattern)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);

        this.context = new DocumentContext("Escreva o conteúdo do relatório aqui...");
        this.controller = new DocumentController(context);

        initComponents();
        context.addListener(this);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Configurações do Documento"));

        topPanel.add(new JLabel("Formato:"));
        formatSelector = new JComboBox<>(new String[]{"Texto Puro", "HTML", "Markdown", "JSON"});
        topPanel.add(formatSelector);

        topPanel.add(new JLabel("Tema UI:"));
        themeSelector = new JComboBox<>(new String[]{"Claro", "Escuro", "Azul"});
        topPanel.add(themeSelector);

        headerCheckBox = new JCheckBox("Incluir Cabeçalho");
        watermarkCheckBox = new JCheckBox("Marca d'Água");
        borderCheckBox = new JCheckBox("Borda Decorativa");
        topPanel.add(headerCheckBox);
        topPanel.add(watermarkCheckBox);
        topPanel.add(borderCheckBox);

        stateLabel = new JLabel(" [Estado: Em Edição] ");
        stateLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        stateLabel.setForeground(new Color(0, 102, 204));
        topPanel.add(stateLabel);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Conteúdo do Documento"));

        contentArea = new JTextArea(context.getContent());
        contentArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollEditor = new JScrollPane(contentArea);
        centerPanel.add(scrollEditor, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Ações e Histórico"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnApplyTheme = new JButton("Aplicar Tema");
        JButton btnClear = new JButton("Limpar Conteúdo");
        JButton btnExport = new JButton("Exportar Documento");

        buttonPanel.add(btnApplyTheme);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExport);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        logArea = new JTextArea(5, 60);
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollLog = new JScrollPane(logArea);
        bottomPanel.add(scrollLog, BorderLayout.CENTER);

        statusLabel = new JLabel(context.getCurrentStatus());
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        btnApplyTheme.addActionListener(e -> {
            String selectedTheme = (String) themeSelector.getSelectedItem();
            controller.handleApplyTheme(selectedTheme);
            applyThemeColors(selectedTheme);
        });

        btnExport.addActionListener(e -> {
            ExportRequest request = new ExportRequest(
                (String) formatSelector.getSelectedItem(),
                headerCheckBox.isSelected(),
                watermarkCheckBox.isSelected(),
                borderCheckBox.isSelected()
            );
            controller.handleExport(request);

            ExportResult result = context.getLastExportResult();
            if (result != null && result.isSuccess()) {
                JOptionPane.showMessageDialog(this,
                    "Documento gerado com sucesso!\n\nConteúdo:\n" + result.getOutputContent(),
                    "Exportação Concluída", JOptionPane.INFORMATION_MESSAGE);
            } else if (result != null && !result.isSuccess()) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao exportar arquivo: " + result.getErrorMessage(),
                    "Erro de I/O", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnClear.addActionListener(e -> controller.handleReset());

        contentArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { notifyChange(); }
            @Override
            public void removeUpdate(DocumentEvent e) { notifyChange(); }
            @Override
            public void changedUpdate(DocumentEvent e) { notifyChange(); }

            private void notifyChange() {
                if (!updatingFromModel) {
                    controller.handleContentEdit(contentArea.getText());
                }
            }
        });
    }

    private void applyThemeColors(String themeName) {
        ThemeStrategy theme = ThemeStrategyFactory.getTheme(themeName);
        ThemeColors colors = theme.getColors();

        contentArea.setBackground(colors.getContentBg());
        contentArea.setForeground(colors.getContentFg());
        logArea.setBackground(colors.getLogBg());
        logArea.setForeground(colors.getLogFg());

        statusLabel.setText(" Status: Tema " + themeName + " aplicado.");
    }

    @Override
    public void onStateChanged(DocumentState newState) {
        stateLabel.setText(" [Estado: " + newState.getStateName() + "] ");
    }

    @Override
    public void onContentChanged(String newContent) {
        updatingFromModel = true;
        if (!contentArea.getText().equals(newContent)) {
            contentArea.setText(newContent);
        }
        updatingFromModel = false;
    }

    @Override
    public void onStatusChanged(String newStatus) {
        statusLabel.setText(newStatus);
    }

    @Override
    public void onLogAdded(String logMessage) {
        logArea.append(logMessage + "\n");
    }
}