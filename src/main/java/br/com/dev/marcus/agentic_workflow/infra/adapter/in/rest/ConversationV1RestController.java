package br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest;

import br.com.dev.marcus.agentic_workflow.app.usecase.ContinueConversationUseCase;
import br.com.dev.marcus.agentic_workflow.app.usecase.StartConversationUseCase;
import br.com.dev.marcus.agentic_workflow.app.usecase.command.ContinueConversationCommand;
import br.com.dev.marcus.agentic_workflow.app.usecase.command.StartConversationCommand;
import br.com.dev.marcus.agentic_workflow.app.usecase.result.ConversationResult;
import br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest.dto.request.ContinueConversationRequestDTO;
import br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest.dto.request.StartConversationRequestDTO;
import br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest.dto.response.ConversationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/conversations")
public class ConversationV1RestController {

    private final StartConversationUseCase startConversationUseCase;
    private final ContinueConversationUseCase continueConversationUseCase;
    
    public ConversationV1RestController(StartConversationUseCase startConversationUseCase, ContinueConversationUseCase continueConversationUseCase) {
        this.startConversationUseCase = startConversationUseCase;
        this.continueConversationUseCase = continueConversationUseCase;
    }

    @PostMapping(version = "1")
    public ResponseEntity<ConversationResponseDTO> start(@RequestBody StartConversationRequestDTO requestDTO) {
        ConversationResult result = startConversationUseCase.execute(new StartConversationCommand(requestDTO.instruction()));
        return ResponseEntity.ok(new ConversationResponseDTO(result.conversationId(), result.response()));
    }

    @PostMapping(
            value = "{conversationId}/messages",
            version = "1"
    )
    public ResponseEntity<ConversationResponseDTO> continueConversation(
            @PathVariable() UUID conversationId,
            @RequestBody ContinueConversationRequestDTO requestDTO
    ) {
        ConversationResult result = continueConversationUseCase.execute(new ContinueConversationCommand(conversationId, requestDTO.instruction()));
        return ResponseEntity.ok(new ConversationResponseDTO(result.conversationId(), result.response()));
    }

}
