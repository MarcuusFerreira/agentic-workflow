package br.com.dev.marcus.agentic_workflow.app.port.out;

import br.com.dev.marcus.agentic_workflow.domain.conversation.Conversation;
import br.com.dev.marcus.agentic_workflow.domain.conversation.ConversationMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConversationMessagePort {

    Conversation saveConversation(Conversation conversation);

    Optional<Conversation> findConversation(UUID conversation);

    ConversationMessage saveMessage(ConversationMessage message);

    List<ConversationMessage> findMessageByConversationId(UUID conversationId);

    Conversation updateConversationTitle(
            UUID conversationId,
            String title
    );

}
