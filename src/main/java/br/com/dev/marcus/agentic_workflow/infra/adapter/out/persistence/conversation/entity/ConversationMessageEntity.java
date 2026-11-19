package br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Table("CONVERSATION_MESSAGES")
public record ConversationMessageEntity(
        @Id
        UUID id,
        @Column("CONVERSATION_ID")
        UUID conversationId,
        @Column("ROLE")
        String role,
        @Column("CONTENT")
        String content,
        @Column("CREATED_AT")
        OffsetDateTime createdAt
) {
}
