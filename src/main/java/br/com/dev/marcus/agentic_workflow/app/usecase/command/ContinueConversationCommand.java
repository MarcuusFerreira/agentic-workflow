package br.com.dev.marcus.agentic_workflow.app.usecase.command;

import java.util.UUID;

public record ContinueConversationCommand(
        UUID conversationId,
        String instruction
) {
}
