package src.pattern.strategy;

public class HtmlFormatStrategy implements FormatStrategy {
    @Override
    public String format(String rawText) {
        return "<html>\n<body>\n<h1>Relatório</h1>\n<p>"
                + rawText.replace("\n", "<br/>")
                + "</p>\n</body>\n</html>\n";
    }

    @Override
    public String getExtension() {
        return "html";
    }
}