package br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.mapper;

import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.entity.ConversationEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.UUID;

public class ConversationRowMapper implements RowMapper<ConversationEntity> {

    @Override
    public ConversationEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ConversationEntity(
                rs.getObject("ID", UUID.class),
                rs.getObject("TITLE", String.class),
                rs.getObject("CREATED_AT", OffsetDateTime.class),
                rs.getObject("UPDATED_AT", OffsetDateTime.class)
        );
    }
}
