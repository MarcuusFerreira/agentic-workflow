package br.com.dev.marcus.agentic_workflow.app.port.out;

import br.com.dev.marcus.agentic_workflow.domain.conversation.ConversationMessage;

import java.util.List;
import java.util.UUID;

public interface ConversationMessagePort {

    List<ConversationMessage> getHistory(UUID conversationId);

    void saveMessage(
            UUID conversationId,
            ConversationMessage message
    );
}
