package br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.repository;

import br.com.dev.marcus.agentic_workflow.domain.conversation.AssistantRole;
import br.com.dev.marcus.agentic_workflow.domain.conversation.MessageRole;
import br.com.dev.marcus.agentic_workflow.domain.conversation.UserRole;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.entity.ConversationMessageEntity;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.mapper.ConversationMessageRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class ConversationMessageJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public ConversationMessageJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ConversationMessageEntity save(ConversationMessageEntity entity) {
        String sql = """
            INSERT INTO conversation_messages (
                ID,
                CONVERSATION_ID,
                ROLE,
                CONTENT,
                CREATED_AT
            ) VALUES (?, ?, ?, ?, ?)
            RETURNING ID, CONVERSATION_ID, ROLE, CONTENT, CREATED_AT
            """;
        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new ConversationMessageEntity(
                        rs.getObject("ID", UUID.class),
                        rs.getObject("CONVERSATION_ID", UUID.class),
                        rs.getString("ROLE"),
                        rs.getString("CONTENT"),
                        rs.getObject("CREATED_AT", OffsetDateTime.class)
                ),
                entity.id(),
                entity.conversationId(),
                entity.role(),
                entity.content(),
                entity.createdAt()
        );
    }

    public void saveAll(List<ConversationMessageEntity> entities) {
        String sql = """
        INSERT INTO conversation_messages (
            ID,
            CONVERSATION_ID,
            ROLE,
            CONTENT,
            CREATED_AT
        ) VALUES (?, ?, ?, ?, ?)
        """;

        jdbcTemplate.batchUpdate(sql, entities, entities.size(), (ps, entity) -> {
            ps.setObject(1, entity.id());
            ps.setObject(2, entity.conversationId());
            ps.setString(3, entity.role());
            ps.setString(4, entity.content());
            ps.setObject(5, entity.createdAt());
        });
    }

    public List<ConversationMessageEntity> findByConversationId(
            UUID conversationId
    ) {
        String sql = """
            SELECT id, conversation_id, role, content, created_at
            FROM conversation_messages
            WHERE conversation_id = ?
            ORDER BY created_at, id
            """;
        return jdbcTemplate.query(
                sql,
                new ConversationMessageRowMapper(),
                conversationId
        );
    }

    private static String toRoleName(MessageRole role) {
        return switch (role) {
            case UserRole r -> "USER";
            case AssistantRole r -> "ASSISTANT";
        };
    }
}
