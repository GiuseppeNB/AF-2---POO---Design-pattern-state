package src.pattern.strategy;

public class JsonFormatStrategy implements FormatStrategy {
    @Override
    public String format(String rawText) {
        String escaped = rawText.replace("\\", "\\\\")
                                .replace("\n", "\\n")
                                .replace("\"", "\\\"");
        return "{\n  \"titulo\": \"Relatório\",\n  \"conteudo\": \""
                + escaped + "\"\n}\n";
    }

    @Override
    public String getExtension() {
        return "json";
    }
}