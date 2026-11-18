package br.com.dev.marcus.agentic_workflow.domain.conversation;

import java.time.OffsetDateTime;

public record ConversationMessage(
        MessageRole role,
        String content,
        OffsetDateTime createdAt
) {
}
