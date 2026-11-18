package br.com.dev.marcus.agentic_workflow.app.usecase.result;

import java.util.UUID;

public record ConversationResult(
        UUID conversationId,
        String response
) {
}
