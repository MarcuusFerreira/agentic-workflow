package br.com.dev.marcus.agentic_workflow.infra.config;

import br.com.dev.marcus.agentic_workflow.app.port.out.UuidGeneratorPort;
import br.com.dev.marcus.agentic_workflow.infra.adapter.out.identifier.UuidV7Generator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdentifierConfiguration {

    @Bean
    public UuidGeneratorPort uuidGeneratorPort() {
        return new UuidV7Generator();
    }

}
