package pattern.state;

import model.DocumentContext;
import pattern.strategy.ExportRequest;
import pattern.strategy.ExportResult;
import pattern.state.DocumentState;

public class ExportedState implements DocumentState {

    @Override
    public void onEditContent(DocumentContext context, String newContent) {
        context.setContentDirectly(newContent);
        context.log("Documento modificado após exportação. Transicionando de volta para 'Em Edição'.");
        context.changeState(new EditingState());
        context.setStatus(" Status: Em Edição (Modificado)");
    }

    @Override
    public void onExport(DocumentContext context, ExportRequest request) {
        context.log("Re-exportando documento no formato: " + request.getFormatName());
        ExportResult result = context.getExportStrategy().export(request, context.getContent());

        if (result.isSuccess()) {
            context.log("Documento re-exportado com sucesso para: " + result.getFileName());
            context.setLastExportResult(result);
            context.setStatus(" Status: Re-exportado como " + request.getFormatName() + " (" + result.getFileName() + ")");
        } else {
            context.log("ERRO ao re-exportar arquivo: " + result.getErrorMessage());
            context.setStatus(" Status: Erro na re-exportação");
        }
    }

    @Override
    public void onReset(DocumentContext context) {
        context.setContentDirectly("");
        context.log("Conteúdo limpo após exportação. Transicionando para 'Em Edição'.");
        context.changeState(new EditingState());
        context.setStatus(" Status: Pronto");
    }

    @Override
    public String getStateName() {
        return "Exportado";
    }

    @Override
    public boolean canEdit() {
        return true;
    }
}