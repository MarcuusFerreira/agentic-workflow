package br.com.dev.marcus.agentic_workflow.infra.adapter.out.llm;

import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import br.com.dev.marcus.agentic_workflow.domain.conversation.AssistantRole;
import br.com.dev.marcus.agentic_workflow.domain.conversation.ConversationMessage;
import br.com.dev.marcus.agentic_workflow.domain.conversation.UserRole;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;

import java.util.List;

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

    @Override
    public String generate(List<ConversationMessage> messages) {
        var springMessages = messages.stream()
                .map(message ->
                    switch (message.role()) {
                        case UserRole role -> (Message) new UserMessage(message.content());
                        case AssistantRole role -> (Message) new AssistantMessage(message.content());
                    }).toArray(Message[]::new);
        return chatClient
                .prompt()
                .messages(springMessages)
                .call()
                .content();
    }

}
