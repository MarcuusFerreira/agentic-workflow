package br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.mapper;

import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.entity.ConversationMessageEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.UUID;

public class ConversationMessageRowMapper implements RowMapper<ConversationMessageEntity> {

    @Override
    public ConversationMessageEntity mapRow(
            ResultSet rs,
            int rowNum
    ) throws SQLException {

        return new ConversationMessageEntity(
                rs.getObject("id", UUID.class),
                rs.getObject("conversation_id", UUID.class),
                rs.getString("role"),
                rs.getString("content"),
                rs.getObject("created_at", OffsetDateTime.class)
        );
    }

}
