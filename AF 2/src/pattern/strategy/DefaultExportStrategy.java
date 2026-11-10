package src.pattern.strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class DefaultExportStrategy implements ExportStrategy {
    @Override
    public ExportResult export(ExportRequest request, String rawText) {
        StringBuilder output = new StringBuilder();

        if (request.isIncludeHeader()) {
            output.append("=== CABEÇALHO OFICIAL DE RELATÓRIO ===\n");
            output.append("Data: ").append(LocalDateTime.now()).append("\n\n");
        }

        if (request.isIncludeWatermark()) {
            output.append("[MARCA D'ÁGUA: CONFIDENCIAL]\n");
        }

        if (request.isIncludeBorder()) {
            output.append("****************************************\n");
        }

        FormatStrategy formatter = FormatStrategyFactory.getStrategy(request.getFormatName());
        output.append(formatter.format(rawText));

        if (request.isIncludeBorder()) {
            output.append("****************************************\n");
        }

        String fileName = "resultado_exportacao." + formatter.getExtension();
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(output.toString());
            return ExportResult.success(fileName, output.toString());
        } catch (IOException ex) {
            return ExportResult.error(ex.getMessage());
        }
    }
}