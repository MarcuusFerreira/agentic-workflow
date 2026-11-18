package br.com.dev.marcus.agentic_workflow.infra.config;

import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import br.com.dev.marcus.agentic_workflow.app.usecase.ExecuteAgentUseCase;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.llm.OllamaLanguageModelAdapter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AgentConfiguration {

    @Bean
    public LanguageModelPort languageModelPort(ChatClient chatClient) {
        return new OllamaLanguageModelAdapter(chatClient);
    }

    @Bean
    public ExecuteAgentUseCase executeAgentUseCase(LanguageModelPort languageModelPort) {
        return new ExecuteAgentUseCase(languageModelPort);
    }

}
