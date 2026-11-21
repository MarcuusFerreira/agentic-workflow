package br.com.dev.marcus.agentic_workflow.app.usecase;


import br.com.dev.marcus.agentic_workflow.app.port.out.ConversationMessagePort;
import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import br.com.dev.marcus.agentic_workflow.app.usecase.command.GenerateTitleCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateTitleUseCase implements UseCase<GenerateTitleCommand, Void> {

    private final LanguageModelPort languageModelPort;
    private final ConversationMessagePort conversationMessagePort;

    private static final Logger log =
            LoggerFactory.getLogger(GenerateTitleUseCase.class);

    private static final int MAX_LENGTH = 100;

    public GenerateTitleUseCase(LanguageModelPort languageModelPort, ConversationMessagePort conversationMessagePort) {
        this.languageModelPort = languageModelPort;
        this.conversationMessagePort = conversationMessagePort;
    }

    @Override
    public Void execute(GenerateTitleCommand input) {
        String title = languageModelPort.generateTitle(input.instruction());
        log.info(title);
        log.info(String.valueOf(title.length()));
        conversationMessagePort.updateConversationTitle(input.conversationId(), title);
        return null;
    }

}
