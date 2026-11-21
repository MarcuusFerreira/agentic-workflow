package br.com.dev.marcus.agentic_workflow.infra.config;

import br.com.dev.marcus.agentic_workflow.app.port.out.ConversationMessagePort;
import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import br.com.dev.marcus.agentic_workflow.app.port.out.UuidGeneratorPort;
import br.com.dev.marcus.agentic_workflow.app.usecase.ContinueConversationUseCase;
import br.com.dev.marcus.agentic_workflow.app.usecase.ExecuteAgentUseCase;
import br.com.dev.marcus.agentic_workflow.app.usecase.GenerateTitleUseCase;
import br.com.dev.marcus.agentic_workflow.app.usecase.StartConversationUseCase;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.llm.OllamaLanguageModelAdapter;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.JdbcConversationMessageAdapter;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.repository.ConversationJdbcRepository;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.persistence.conversation.repository.ConversationMessageJdbcRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Bean
    public StartConversationUseCase startConversationUseCase(
            ConversationMessagePort conversationMessagePort,
            LanguageModelPort languageModelPort,
            UuidGeneratorPort uuidGeneratorPort,
            GenerateTitleUseCase generateTitleUseCase,
            ExecutorService executorService
    ) {
        return new StartConversationUseCase(
                conversationMessagePort,
                languageModelPort,
                uuidGeneratorPort,
                generateTitleUseCase,
                executorService
        );
    }

    @Bean
    public ContinueConversationUseCase continueConversationUseCase(ConversationMessagePort conversationMessagePort, LanguageModelPort languageModelPort, UuidGeneratorPort uuidGeneratorPort) {
        return new ContinueConversationUseCase(conversationMessagePort, languageModelPort, uuidGeneratorPort);
    }

    @Bean
    public ConversationMessagePort conversationMessagePort(
            ConversationJdbcRepository conversationRepository,
            ConversationMessageJdbcRepository messageRepository
    ) {
        return new JdbcConversationMessageAdapter(conversationRepository, messageRepository);
    }

    @Bean
    public GenerateTitleUseCase generateTitleUseCase(LanguageModelPort languageModelPort, ConversationMessagePort conversationMessagePort) {
        return new GenerateTitleUseCase(languageModelPort, conversationMessagePort);
    }

    @Bean(destroyMethod = "close")
    public ExecutorService executorService() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
