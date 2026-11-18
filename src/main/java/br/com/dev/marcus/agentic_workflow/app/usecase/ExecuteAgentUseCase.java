package br.com.dev.marcus.agentic_workflow.app.usecase;

import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;

public class ExecuteAgentUseCase implements UseCase<String, String> {

    private final LanguageModelPort languageModelPort;

    public ExecuteAgentUseCase(LanguageModelPort languageModelPort) {
        this.languageModelPort = languageModelPort;
    }

    @Override
    public String execute(String input) {
        return this.languageModelPort.generate(input);
    }

}
