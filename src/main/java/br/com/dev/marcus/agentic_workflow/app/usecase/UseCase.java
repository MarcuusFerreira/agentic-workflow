package br.com.dev.marcus.agentic_workflow.app.usecase;

public interface UseCase<I, O> {

    O execute(I input);

}
