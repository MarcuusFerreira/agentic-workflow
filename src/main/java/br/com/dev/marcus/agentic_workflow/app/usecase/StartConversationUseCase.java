package br.com.dev.marcus.agentic_workflow.app.usecase;

import br.com.dev.marcus.agentic_workflow.app.port.out.ConversationMessagePort;
import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import br.com.dev.marcus.agentic_workflow.app.port.out.UuidGeneratorPort;
import br.com.dev.marcus.agentic_workflow.app.usecase.command.StartConversationCommand;
import br.com.dev.marcus.agentic_workflow.app.usecase.result.ConversationResult;
import br.com.dev.marcus.agentic_workflow.domain.conversation.AssistantRole;
import br.com.dev.marcus.agentic_workflow.domain.conversation.Conversation;
import br.com.dev.marcus.agentic_workflow.domain.conversation.ConversationMessage;
import br.com.dev.marcus.agentic_workflow.domain.conversation.UserRole;

import java.time.OffsetDateTime;
import java.util.UUID;

public class StartConversationUseCase implements UseCase<StartConversationCommand, ConversationResult> {

    private final ConversationMessagePort conversationMessagePort;
    private final LanguageModelPort languageModelPort;
    private final UuidGeneratorPort uuidGeneratorPort;

    public StartConversationUseCase(ConversationMessagePort conversationMessagePort, LanguageModelPort languageModelPort, UuidGeneratorPort uuidGeneratorPort) {
        this.conversationMessagePort = conversationMessagePort;
        this.languageModelPort = languageModelPort;
        this.uuidGeneratorPort = uuidGeneratorPort;
    }

    @Override
    public ConversationResult execute(StartConversationCommand input) {
        String result = languageModelPort.generate(input.instruction());
        UUID conversationId = uuidGeneratorPort.generate();
        conversationMessagePort.saveMessage(
                conversationId,
                new ConversationMessage(
                        new UserRole(),
                        input.instruction(),
                        OffsetDateTime.now()
                )
        );
        conversationMessagePort.saveMessage(
                conversationId,
                new ConversationMessage(
                        new AssistantRole(),
                        result,
                        OffsetDateTime.now()
                )
        );
        return new ConversationResult(conversationId, result);
    }

}
