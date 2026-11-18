package br.com.dev.marcus.agentic_workflow.domain.conversation;

public sealed interface MessageRole permits AssistantRole, UserRole {
}
