package br.com.dev.marcus.agentic_workflow.app.usecase;

import br.com.dev.marcus.agentic_workflow.app.port.out.ConversationMessagePort;
import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import br.com.dev.marcus.agentic_workflow.app.port.out.UuidGeneratorPort;
import br.com.dev.marcus.agentic_workflow.app.usecase.command.ContinueConversationCommand;
import br.com.dev.marcus.agentic_workflow.app.usecase.result.ConversationResult;
import br.com.dev.marcus.agentic_workflow.domain.conversation.AssistantRole;
import br.com.dev.marcus.agentic_workflow.domain.conversation.Conversation;
import br.com.dev.marcus.agentic_workflow.domain.conversation.ConversationMessage;
import br.com.dev.marcus.agentic_workflow.domain.conversation.UserRole;
import br.com.dev.marcus.agentic_workflow.domain.exception.ConversationNotFoundException;

import java.util.List;
import java.util.stream.Stream;

public class ContinueConversationUseCase implements UseCase<ContinueConversationCommand, ConversationResult>{

    private final ConversationMessagePort conversationMessagePort;
    private final LanguageModelPort languageModelPort;
    private final UuidGeneratorPort uuidGeneratorPort;

    public ContinueConversationUseCase(ConversationMessagePort conversationMessagePort, LanguageModelPort languageModelPort, UuidGeneratorPort uuidGeneratorPort) {
        this.conversationMessagePort = conversationMessagePort;
        this.languageModelPort = languageModelPort;
        this.uuidGeneratorPort = uuidGeneratorPort;
    }

    @Override
    public ConversationResult execute(ContinueConversationCommand input) {
        Conversation conversation = conversationMessagePort
                .findConversation(input.conversationId())
                .orElseThrow(() -> new ConversationNotFoundException("Conversation not found"));
        List<ConversationMessage> history = conversationMessagePort.findMessageByConversationId(input.conversationId());
        ConversationMessage userMessage = conversation.postMessage(
                uuidGeneratorPort.generate(),
                new UserRole(),
                input.instruction()

        );
        List<ConversationMessage> context = Stream.concat(
                history.stream(),
                Stream.of(userMessage)
        ).toList();
        String answer = languageModelPort.generate(context);
        ConversationMessage assistantMessage = conversation.postMessage(
                uuidGeneratorPort.generate(),
                new AssistantRole(),
                answer
        );
        conversationMessagePort.saveMessage(userMessage);
        conversationMessagePort.saveMessage(assistantMessage);
        return new ConversationResult(conversation.id(), answer);
    }

}
