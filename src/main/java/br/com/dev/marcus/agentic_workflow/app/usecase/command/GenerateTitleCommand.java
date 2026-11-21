package br.com.dev.marcus.agentic_workflow.app.usecase.command;

import java.util.UUID;

public record GenerateTitleCommand(
        UUID conversationId,
        String instruction
) {
}
