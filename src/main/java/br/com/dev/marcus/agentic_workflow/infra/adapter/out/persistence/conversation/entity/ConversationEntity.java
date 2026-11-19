package br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Table("CONVERSATIONS")
public record ConversationEntity(
        @Id
        UUID id,
        @Column("TITLE")
        String title,
        @Column("CREATED_AT")
        OffsetDateTime createdAt,
        @Column("UPDATED_AT")
        OffsetDateTime updatedAt
) {
}
