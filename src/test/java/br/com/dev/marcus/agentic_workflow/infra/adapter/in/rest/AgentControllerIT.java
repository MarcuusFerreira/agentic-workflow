package br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest;

import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AgentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LanguageModelPort languageModelPort;

    @Test
    @DisplayName("Should execute agent and return response")
    void shouldExecuteAgentAndReturnResponse() throws Exception {
        String instruction = "Explique Clean Architecture";
        String expectedResponse = "Clean Architecture é uma arquitetura de software.";

        when(languageModelPort.generate(instruction))
                .thenReturn(expectedResponse);

        mockMvc.perform(post("/api/agents/execute")
                        .header("X-API-Version", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "instruction": "Explique Clean Architecture"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.response").value(expectedResponse));

        verify(languageModelPort).generate(instruction);
    }


    @Test
    @DisplayName("Should return bad request when interaction is blank")
    void shouldReturnBadRequestWhenInteractionIsBlank() throws Exception {
        mockMvc.perform(post("/api/agents/execute")
                        .header("X-API-Version", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "instruction": ""
                                }
                                """))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(languageModelPort);
    }
}
