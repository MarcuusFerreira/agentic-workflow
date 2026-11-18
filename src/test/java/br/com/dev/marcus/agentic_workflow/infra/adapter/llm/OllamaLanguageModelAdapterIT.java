package br.com.dev.marcus.agentic_workflow.infra.adapter.llm;

import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("Ollama")
@SpringBootTest
public class OllamaLanguageModelAdapterIT {

    @Autowired
    private LanguageModelPort languageModelPort;

    @Test
    @DisplayName("Should call ChatModel and return a respose")
    void shouldCallChatModelAndReturnAResponse() {
        String response = languageModelPort.generate(
                "Explique o que é Clean Architecture em três frases."
        );
        assertNotNull(response);
        assertFalse(response.isBlank());
        System.out.println(response);
    }

}
