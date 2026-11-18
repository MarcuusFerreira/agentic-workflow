package br.com.dev.marcus.agentic_workflow.app.usecase;

import br.com.dev.marcus.agentic_workflow.app.port.out.ConversationMessagePort;
import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import br.com.dev.marcus.agentic_workflow.app.usecase.command.ContinueConversationCommand;
import br.com.dev.marcus.agentic_workflow.app.usecase.result.ConversationResult;
import br.com.dev.marcus.agentic_workflow.domain.conversation.AssistantRole;
import br.com.dev.marcus.agentic_workflow.domain.conversation.ConversationMessage;
import br.com.dev.marcus.agentic_workflow.domain.conversation.UserRole;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ContinueConversationUseCase implements UseCase<ContinueConversationCommand, ConversationResult>{

    private final ConversationMessagePort conversationMessagePort;
    private final LanguageModelPort languageModelPort;

    public ContinueConversationUseCase(ConversationMessagePort conversationMessagePort, LanguageModelPort languageModelPort) {
        this.conversationMessagePort = conversationMessagePort;
        this.languageModelPort = languageModelPort;
    }

    @Override
    public ConversationResult execute(ContinueConversationCommand input) {
        var history = conversationMessagePort.getHistory(input.conversationId());
        var newMessage = new ConversationMessage(
                new UserRole(),
                input.instruction(),
                OffsetDateTime.now()
        );
        List<ConversationMessage> listWithNewMessage = new ArrayList<>(history);
        listWithNewMessage.add(newMessage);
        String result = languageModelPort.generate(listWithNewMessage);
        conversationMessagePort.saveMessage(
                input.conversationId(),
                newMessage
        );
        conversationMessagePort.saveMessage(
                input.conversationId(),
                new ConversationMessage(
                        new AssistantRole(),
                        result,
                        OffsetDateTime.now()
                )
        );
        return new ConversationResult(input.conversationId(), result);
    }

}
