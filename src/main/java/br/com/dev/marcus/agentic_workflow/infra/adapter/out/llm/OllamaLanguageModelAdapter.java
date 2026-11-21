package br.com.dev.marcus.agentic_workflow.infra.adapter.out.llm;

import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import br.com.dev.marcus.agentic_workflow.domain.conversation.AssistantRole;
import br.com.dev.marcus.agentic_workflow.domain.conversation.ConversationMessage;
import br.com.dev.marcus.agentic_workflow.domain.conversation.UserRole;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.List;

public class OllamaLanguageModelAdapter implements LanguageModelPort {

    private final ChatClient chatClient;

    private static final String TITLE_SYSTEM_PROMPT = """
    Você é um gerador de títulos para conversas.

    Sua tarefa é identificar o assunto principal da mensagem
    e criar um título que represente esse assunto.

    REGRAS:
    1. Gere apenas um título.
    2. Utilize no máximo 60 caracteres.
    3. Utilize entre 3 e 8 palavras.
    4. Preserve o idioma da mensagem original.
    5. Seja específico e objetivo.
    6. Não responda à mensagem do usuário.
    7. Não explique o título.
    8. Não utilize aspas ou pontuação final.
    9. Não inclua prefixos como "Título:".

    EXEMPLOS:

    Entrada: Como implementar autenticação JWT com Spring Boot?
    Saída: Implementação de JWT com Spring Boot

    Entrada: Quero entender como funciona o Kafka.
    Saída: Introdução ao Apache Kafka

    Entrada: Explique a diferença entre arquitetura hexagonal
    e arquitetura limpa.
    Saída: Arquitetura Hexagonal vs Clean Architecture

    Retorne exclusivamente o título.
    """;

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

    @Override
    public String generateTitle(String instruction) {
        String title = chatClient
                .prompt()
                .system(TITLE_SYSTEM_PROMPT)
                .user("Mensagem: " + instruction)
                .call()
                .content();
        return normalizeTitle(title);
    }

    private String normalizeTitle(String title) {
        if (title == null || title.isBlank()) {
            return "Nova conversa";
        }

        String normalized = title
                .replaceAll("[\\r\\n]+", " ")
                .replaceAll("\\s+", " ")
                .replaceAll("^['\"“”]+|['\"“”]+$", "")
                .trim();

        if (normalized.length() > 100) {
            normalized = normalized.substring(0, 100).trim();
        }

        return normalized;
    }

}
