package br.com.dev.marcus.agentic_workflow.app.usecase;

import br.com.dev.marcus.agentic_workflow.app.port.out.ConversationMessagePort;
import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import br.com.dev.marcus.agentic_workflow.app.port.out.UuidGeneratorPort;
import br.com.dev.marcus.agentic_workflow.app.usecase.command.GenerateTitleCommand;
import br.com.dev.marcus.agentic_workflow.app.usecase.command.StartConversationCommand;
import br.com.dev.marcus.agentic_workflow.app.usecase.result.ConversationResult;
import br.com.dev.marcus.agentic_workflow.domain.conversation.AssistantRole;
import br.com.dev.marcus.agentic_workflow.domain.conversation.Conversation;
import br.com.dev.marcus.agentic_workflow.domain.conversation.ConversationMessage;
import br.com.dev.marcus.agentic_workflow.domain.conversation.UserRole;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

public class StartConversationUseCase implements UseCase<StartConversationCommand, ConversationResult> {

    private final ConversationMessagePort conversationMessagePort;
    private final LanguageModelPort languageModelPort;
    private final UuidGeneratorPort uuidGeneratorPort;
    private final GenerateTitleUseCase generateTitleUseCase;
    private final Executor executor;

    public StartConversationUseCase(
            ConversationMessagePort conversationMessagePort,
            LanguageModelPort languageModelPort,
            UuidGeneratorPort uuidGeneratorPort,
            GenerateTitleUseCase generateTitleUseCase,
            Executor executor
    ) {
        this.conversationMessagePort = conversationMessagePort;
        this.languageModelPort = languageModelPort;
        this.uuidGeneratorPort = uuidGeneratorPort;
        this.generateTitleUseCase = generateTitleUseCase;
        this.executor = executor;
    }

    @Override
    public ConversationResult execute(StartConversationCommand input) {
        String answer = languageModelPort.generate(input.instruction());
        UUID conversationId = uuidGeneratorPort.generate();
        Conversation conversation = Conversation.create(conversationId);
        ConversationMessage userMessage = conversation.postMessage(
                uuidGeneratorPort.generate(),
                new UserRole(),
                input.instruction()
        );
        ConversationMessage assistantMessage = conversation.postMessage(
                uuidGeneratorPort.generate(),
                new AssistantRole(),
                answer
        );
        conversationMessagePort.saveConversationWithMessages(
                conversation,
                List.of(userMessage, assistantMessage)
        );
        executor.execute(() -> generateTitleUseCase.execute(new GenerateTitleCommand(conversationId, input.instruction())));
        return new ConversationResult(conversationId, answer);
    }
}
