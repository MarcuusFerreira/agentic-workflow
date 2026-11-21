package br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.mapper;

import br.com.dev.marcus.agentic_workflow.domain.conversation.*;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.entity.ConversationMessageEntity;

public final class ConversationMessageMapper {

    private ConversationMessageMapper() {}

    public static ConversationMessageEntity toEntity(ConversationMessage message) {
        return new ConversationMessageEntity(
                message.id(),
                message.conversationId(),
                toRoleName(message.role()),
                message.content(),
                message.createdAt()
        );
    }

    public static ConversationMessage toDomain(ConversationMessageEntity entity) {
        return ConversationMessage.restore(
                entity.id(),
                entity.conversationId(),
                toRole(entity.role()),
                entity.content(),
                entity.createdAt()
        );
    }

    private static String toRoleName(MessageRole role) {
        return switch (role) {
            case UserRole r -> "USER";
            case AssistantRole r -> "ASSISTANT";
        };
    }

    private static MessageRole toRole(String roleName) {
        return switch (roleName) {
            case "USER" -> new UserRole();
            case "ASSISTANT" -> new AssistantRole();
            default -> throw new IllegalArgumentException("Unsupported role: " + roleName);
        };
    }
}