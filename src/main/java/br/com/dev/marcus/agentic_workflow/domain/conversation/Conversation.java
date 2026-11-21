package br.com.dev.marcus.agentic_workflow.domain.conversation;

import br.com.dev.marcus.agentic_workflow.domain.exception.TitleIsRequiredException;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public record Conversation(
        UUID id,
        String title,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {

    public Conversation{
        Objects.requireNonNull(id, "Id is required!");
        Objects.requireNonNull(createdAt, "Created At is required!");
        Objects.requireNonNull(updatedAt, "Updated At is required!");
        validateTile(title);
    }

    public static Conversation create(UUID id) {
        return new Conversation(
                id,
                "New Conversation",
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );
    }

    public static Conversation restore(
            UUID id,
            String title,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt
    ) {
        return new Conversation(id, title, createdAt, updatedAt);
    }

    public Conversation rename(String newTitle) {
        return new Conversation(id, newTitle, createdAt, OffsetDateTime.now());
    }

    public ConversationMessage postMessage(UUID messageId, MessageRole role, String content) {
        return ConversationMessage.create(messageId, id, role, content);
    }

    private static void validateTile(String title) {
        if (title == null || title.isBlank()) {
            throw new TitleIsRequiredException("Title cannot be empty");
        }
    }
}
