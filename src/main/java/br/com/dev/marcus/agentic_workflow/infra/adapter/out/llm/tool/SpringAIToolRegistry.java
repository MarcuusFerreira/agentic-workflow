package br.com.dev.marcus.agentic_workflow.infra.adapter.out.llm.tool;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringAIToolRegistry implements ToolCallbackProvider {

    private final ToolCallbackProvider delegate;

    public SpringAIToolRegistry(List<AgentTool> tools) {
        Object[] toolObjects = tools.toArray();
        this.delegate = MethodToolCallbackProvider.builder()
                .toolObjects(toolObjects)
                .build();
    }

    @Override
    public ToolCallback[] getToolCallbacks() {
        return delegate.getToolCallbacks();
    }
}
