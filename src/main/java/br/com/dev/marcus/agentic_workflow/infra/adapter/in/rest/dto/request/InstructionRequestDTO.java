package br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record InstructionRequestDTO(
        @Length(max = 1000)
        @NotBlank
        String instruction
) {
}
