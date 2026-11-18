package br.com.dev.marcus.agentic_workflow.infra.adapter.out.llm.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculatorTool {

    private static final Logger log = LoggerFactory.getLogger(CalculatorTool.class);

    @Tool(description = "Performs multiplication between two numbers. Use this tool whenever the user asks to multiply values")
    public BigDecimal multiply(
            @ToolParam(description = "First number") BigDecimal first,
            @ToolParam(description = "Second number") BigDecimal second
    ) {
        log.info("Calculator tool executed");
        log.info("First: {}", first);
        log.info("Second: {}", second);
        return first.multiply(second);
    }

}
