package br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation;

import br.com.dev.marcus.agentic_workflow.app.port.out.ConversationMessagePort;
import br.com.dev.marcus.agentic_workflow.domain.conversation.AssistantRole;
import br.com.dev.marcus.agentic_workflow.domain.conversation.Conversation;
import br.com.dev.marcus.agentic_workflow.domain.conversation.ConversationMessage;
import br.com.dev.marcus.agentic_workflow.domain.conversation.UserRole;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.entity.ConversationEntity;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.entity.ConversationMessageEntity;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.mapper.ConversationMapper;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.mapper.ConversationMessageMapper;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.repository.ConversationJdbcRepository;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.repository.ConversationMessageJdbcRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcConversationMessageAdapter implements ConversationMessagePort {

    private final ConversationJdbcRepository conversationRepository;
    private final ConversationMessageJdbcRepository messageRepository;

    public JdbcConversationMessageAdapter(
            ConversationJdbcRepository conversationRepository,
            ConversationMessageJdbcRepository messageRepository
    ) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public Conversation saveConversation(Conversation conversation) {
         var saved = conversationRepository.save(new ConversationEntity(
                conversation.id(),
                conversation.title(),
                conversation.createdAt(),
                conversation.updatedAt()
        ));
        return new Conversation(
                saved.id(),
                saved.title(),
                saved.createdAt(),
                saved.updatedAt()
        );
    }

    @Override
    public Optional<Conversation> findConversation(UUID conversation) {
        return conversationRepository
                .findById(conversation)
                .map(entity -> new Conversation(
                        entity.id(),
                        entity.title(),
                        entity.createdAt(),
                        entity.updatedAt()
                ));
    }

    @Override
    public ConversationMessage saveMessage(ConversationMessage message) {
        var entity = messageRepository.save(
                ConversationMessageMapper.toEntity(message)
        );
        return ConversationMessageMapper.toDomain(entity);
    }

    @Override
    public List<ConversationMessage> findMessageByConversationId(UUID conversationId) {
        return messageRepository.findByConversationId(conversationId).stream()
                .map(message -> new ConversationMessage(
                        message.id(),
                        message.conversationId(),
                        switch (message.role()) {
                            case "USER" -> new UserRole();
                            case "ASSISTANT" -> new AssistantRole();
                            default -> throw new IllegalStateException("Unexpected value: " + message.role());
                        },
                        message.content(),
                        message.createdAt()
                ))
                .toList();
    }

    @Override
    public Conversation updateConversationTitle(UUID conversationId, String title) {
        var entity = conversationRepository.updateConversationTitle(conversationId, title);
        return new Conversation(
                entity.id(),
                entity.title(),
                entity.createdAt(),
                entity.updatedAt()
        );
    }

    @Override
    public void saveConversationWithMessages(Conversation conversation, List<ConversationMessage> messages) {
        conversationRepository.save(
                ConversationMapper.toEntity(conversation)
        );
        List<ConversationMessageEntity> messageEntities = messages.stream()
                .map(ConversationMessageMapper::toEntity)
                .toList();
        messageRepository.saveAll(
                messageEntities
        );
    }
}
