package br.com.dev.marcus.agentic_workflow.domain.conversation;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ConversationMessage(
        UUID id,
        UUID conversationId,
        MessageRole role,
        String content,
        OffsetDateTime createdAt
) {

    static ConversationMessage create(UUID messageId, UUID conversationId, MessageRole role, String content) {
        return new ConversationMessage(messageId, conversationId, role, content, OffsetDateTime.now());
    }

    public static ConversationMessage restore(UUID id, UUID conversationId, MessageRole role, String content, OffsetDateTime createdAt) {
        return new ConversationMessage(id, conversationId, role, content, createdAt);
    }
}
