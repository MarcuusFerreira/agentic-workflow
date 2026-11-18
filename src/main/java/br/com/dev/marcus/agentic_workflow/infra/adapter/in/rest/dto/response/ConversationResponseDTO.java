package br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest.dto.response;

import java.util.UUID;

public record ConversationResponseDTO(
        UUID conversationId,
        String result
) {
}
