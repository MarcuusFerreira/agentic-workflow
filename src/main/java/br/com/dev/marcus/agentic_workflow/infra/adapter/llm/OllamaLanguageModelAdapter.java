package br.com.dev.marcus.agentic_workflow.infra.adapter.llm;

import br.com.dev.marcus.agentic_workflow.app.port.out.LanguageModelPort;
import org.springframework.ai.chat.model.ChatModel;

public class OllamaLanguageModelAdapter implements LanguageModelPort {

    private final ChatModel chatModel;

    public OllamaLanguageModelAdapter(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public String generate(String instruction) {
        return chatModel.call(instruction);
    }

}
