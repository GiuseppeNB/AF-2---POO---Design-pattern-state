package pattern.state;

import model.DocumentContext;
import pattern.strategy.ExportRequest;
import pattern.strategy.ExportResult;
import pattern.state.DocumentState;

public class EditingState implements DocumentState {

    @Override
    public void onEditContent(DocumentContext context, String newContent) {
        context.setContentDirectly(newContent);
        context.log("Conteúdo atualizado no estado de edição.");
    }

    @Override
    public void onExport(DocumentContext context, ExportRequest request) {
        context.log("Iniciando exportação no formato: " + request.getFormatName());
        ExportResult result = context.getExportStrategy().export(request, context.getContent());

        if (result.isSuccess()) {
            context.log("Documento exportado com sucesso para: " + result.getFileName());
            context.setLastExportResult(result);
            context.changeState(new ExportedState());
            context.setStatus(" Status: Exportado como " + request.getFormatName() + " (" + result.getFileName() + ")");
        } else {
            context.log("ERRO ao exportar arquivo: " + result.getErrorMessage());
            context.setStatus(" Status: Erro na exportação");
        }
    }

    @Override
    public void onReset(DocumentContext context) {
        context.setContentDirectly("");
        context.log("Conteúdo limpo pelo usuário.");
        context.setStatus(" Status: Edição limpa");
    }

    @Override
    public String getStateName() {
        return "Em Edição";
    }

    @Override
    public boolean canEdit() {
        return true;
    }
}