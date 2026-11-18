package br.com.dev.marcus.agentic_workflow.infra.adapter.out.memory;

import br.com.dev.marcus.agentic_workflow.app.port.out.ConversationMessagePort;
import br.com.dev.marcus.agentic_workflow.domain.conversation.ConversationMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryConversationAdapter implements ConversationMessagePort {

    private final ConcurrentHashMap<UUID, List<ConversationMessage>> conversations = new ConcurrentHashMap<>();

    @Override
    public List<ConversationMessage> getHistory(UUID conversationId) {
        return conversations.getOrDefault(conversationId, List.of());
    }

    @Override
    public void saveMessage(UUID conversationId, ConversationMessage message) {
        conversations.compute(conversationId, (key, history) -> {
            var messages = history == null
                    ? new ArrayList<ConversationMessage>()
                    : new ArrayList<>(history);
            messages.add(message);
            return List.copyOf(messages);
        });
    }
}
