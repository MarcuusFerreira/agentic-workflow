package br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest;

import br.com.dev.marcus.agentic_workflow.app.usecase.ExecuteAgentUseCase;
import br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest.dto.request.InstructionRequestDTO;
import br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest.dto.response.InstructionResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final ExecuteAgentUseCase executeAgentUseCase;

    public AgentController(ExecuteAgentUseCase executeAgentUseCase) {
        this.executeAgentUseCase = executeAgentUseCase;
    }

    @PostMapping(
            value = "execute",
            version = "1"
    )
    public ResponseEntity<InstructionResponseDTO> execute(
            @Valid
            @RequestBody
            InstructionRequestDTO dto
    ) {
        String response = this.executeAgentUseCase.execute(dto.instruction());
        return ResponseEntity.ok(new InstructionResponseDTO(response));
    }

}
