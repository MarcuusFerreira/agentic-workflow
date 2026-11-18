package br.com.dev.marcus.agentic_workflow.app.usecase;

import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ExecuteAgentUseCaseTest {

    @Mock
    private LanguageModelPort languageModelPort;

    @InjectMocks
    private ExecuteAgentUseCase useCase;

    @Test
    @DisplayName("Should return message when connect AI model")
    void shouldReturnMessageWhenConnectAIModel() {
        String instruction = "teste";
        String expectedResponse = "Message";
        when(languageModelPort.generate(instruction))
                .thenReturn(expectedResponse);
        String response = useCase.execute(instruction);
        assertEquals(expectedResponse, response);
        verify(languageModelPort).generate(instruction);
    }

}
