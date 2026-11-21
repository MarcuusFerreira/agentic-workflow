package br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.mapper;

import br.com.dev.marcus.agentic_workflow.domain.conversation.Conversation;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.entity.ConversationEntity;

public final class ConversationMapper {

    private ConversationMapper() {}

    public static ConversationEntity toEntity(Conversation conversation) {
        return new ConversationEntity(
                conversation.id(),
                conversation.title(),
                conversation.createdAt(),
                conversation.updatedAt()
        );
    }

    public static Conversation toDomain(ConversationEntity entity) {
        return Conversation.restore(
                entity.id(),
                entity.title(),
                entity.createdAt(),
                entity.updatedAt()
        );
    }

}
