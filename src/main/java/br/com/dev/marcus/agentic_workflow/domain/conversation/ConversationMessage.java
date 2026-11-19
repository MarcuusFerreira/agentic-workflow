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
}
