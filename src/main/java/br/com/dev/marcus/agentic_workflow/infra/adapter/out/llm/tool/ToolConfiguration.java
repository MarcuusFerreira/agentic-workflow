package br.com.dev.marcus.agentic_workflow.infra.adapter.out.llm.tool;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfiguration {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder, SpringAIToolRegistry toolRegistry) {
        return builder
                .defaultTools(toolRegistry)
                .build();
    }
}
