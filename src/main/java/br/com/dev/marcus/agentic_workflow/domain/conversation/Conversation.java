package br.com.dev.marcus.agentic_workflow.domain.conversation;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Conversation(
        UUID id,
        String title,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
