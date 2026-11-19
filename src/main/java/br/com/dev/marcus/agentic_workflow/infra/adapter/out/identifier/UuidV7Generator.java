package br.com.dev.marcus.agentic_workflow.infra.adapter.out.identifier;

import br.com.dev.marcus.agentic_workflow.app.port.out.UuidGeneratorPort;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;

import java.util.UUID;

public class UuidV7Generator implements UuidGeneratorPort {

    private static final TimeBasedEpochGenerator timeBasedEpochGenerator =
            Generators.timeBasedEpochGenerator();

    public UuidV7Generator() {}

    @Override
    public UUID generate() {
        return timeBasedEpochGenerator.generate();
    }
}
