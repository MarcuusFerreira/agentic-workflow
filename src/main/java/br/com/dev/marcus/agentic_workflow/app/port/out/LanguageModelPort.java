package br.com.dev.marcus.agentic_workflow.app.port.out;

import br.com.dev.marcus.agentic_workflow.domain.conversation.ConversationMessage;

import java.util.List;

public interface LanguageModelPort {

    String generate(String instruction);

    String generate(List<ConversationMessage> messages);

    String generateTitle(String instruction);

}
