
package br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest.exception;

import br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest.dto.response.ApiExceptionResponseDTO;
import br.com.dev.marcus.agentic_workflow.infra.adapter.in.rest.dto.response.FieldResponseDTO;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {

        HttpStatus status = HttpStatus.resolve(statusCode.value());

        ApiExceptionResponseDTO dto = new ApiExceptionResponseDTO(
                statusCode.value(),
                status != null ? status.getReasonPhrase() : "HTTP error",
                "Request could not be processed.",
                OffsetDateTime.now(),
                replaceUri(request),
                null
        );

        return super.handleExceptionInternal(
                ex, dto, headers, statusCode, request
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {

        List<FieldResponseDTO> fields = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage() != null
                                ? error.getDefaultMessage()
                                : "Invalid value",
                        (first, second) -> first + "; " + second,
                        LinkedHashMap::new
                ))
                .entrySet()
                .stream()
                .map(entry -> new FieldResponseDTO(
                        entry.getKey(),
                        entry.getValue()
                ))
                .toList();

        ApiExceptionResponseDTO dto = new ApiExceptionResponseDTO(
                statusCode.value(),
                "Validation error",
                "One or more fields are invalid.",
                OffsetDateTime.now(),
                replaceUri(request),
                fields
        );

        return super.handleExceptionInternal(
                ex, dto, headers, statusCode, request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponseDTO> handleUnexpected(
            Exception ex,
            WebRequest request) {

        log.error("Unexpected application error", ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiExceptionResponseDTO dto = new ApiExceptionResponseDTO(
                status.value(),
                "System error",
                "Internal application error occurred, please contact support.",
                OffsetDateTime.now(),
                replaceUri(request),
                null
        );

        return ResponseEntity.status(status).body(dto);
    }

    private static String replaceUri(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}
