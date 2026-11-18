package br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;
import java.util.List;

public record ApiExceptionResponseDTO(
        int status,
        String title,
        String detail,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ssXXX")
        OffsetDateTime dateTime,
        String path,
        List<FieldResponseDTO> fields
) {
}
