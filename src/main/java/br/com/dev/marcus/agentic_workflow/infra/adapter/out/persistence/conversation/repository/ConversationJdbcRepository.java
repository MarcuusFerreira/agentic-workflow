package br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.repository;

import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.entity.ConversationEntity;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.mapper.ConversationRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ConversationJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public ConversationJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ConversationEntity save(ConversationEntity entity) {
        String sql = """
            INSERT INTO CONVERSATIONS (
                ID,
                TITLE,
                CREATED_AT,
                UPDATED_AT
            ) VALUES (?, ?, ?, ?)
            RETURNING ID, TITLE, CREATED_AT, UPDATED_AT
            """;
        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new ConversationEntity(
                        rs.getObject("ID", UUID.class),
                        rs.getString("TITLE"),
                        rs.getObject("CREATED_AT", OffsetDateTime.class),
                        rs.getObject("UPDATED_AT", OffsetDateTime.class)
                ),
                entity.id(),
                entity.title(),
                entity.createdAt(),
                entity.updatedAt()
        );
    }

    public Optional<ConversationEntity> findById(UUID id) {
        String sql = """
            SELECT ID, CREATED_AT, UPDATED_AT
            FROM CONVERSATIONS
            WHERE ID = ?
            """;
        return jdbcTemplate.query(
                sql,
                new ConversationRowMapper(),
                id
        ).stream().findFirst();
    }

    public ConversationEntity updateConversationTitle(UUID conversationId, String title) {
        String sql = """
                UPDATE CONVERSATIONS
                SET TITLE = ?, UPDATED_AT = ?
                WHERE ID = ?
                RETURNING ID, TITLE, CREATED_AT, UPDATED_AT
                """;
        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new ConversationEntity(
                        rs.getObject("ID", UUID.class),
                        rs.getString("TITLE"),
                        rs.getObject("CREATED_AT", OffsetDateTime.class),
                        rs.getObject("UPDATED_AT", OffsetDateTime.class)
                ),
                title,
                OffsetDateTime.now(),
                conversationId
        );
    }

}
