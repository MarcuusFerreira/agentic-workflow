package br.com.dev.marcus.agentic_workflow.infra.adapter.out.llm;

import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;

public class OllamaLanguageModelAdapter implements LanguageModelPort {

    private final ChatClient chatClient;

    public OllamaLanguageModelAdapter(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String generate(String instruction) {
        return chatClient.prompt()
                .user(instruction)
                .call()
                .content();
    }

}
