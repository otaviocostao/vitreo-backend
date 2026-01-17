package com.api.vitreo.exception;

import com.api.vitreo.dto.ApiErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404
        ApiErrorDTO error = new ApiErrorDTO(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(), // "Not Found"
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorDTO> handleBusinessException(BusinessException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400
        ApiErrorDTO error = new ApiErrorDTO(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(), // "Bad Request"
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {

        Throwable rootCause = ex.getRootCause();
        String message = "Erro de integridade de dados. Verifique os valores enviados.";

        if (rootCause != null && rootCause.getMessage() != null) {
            String rootCauseMessage = rootCause.getMessage().toLowerCase();

            if (rootCauseMessage.contains("pedidos_ordem_servico_key") || rootCauseMessage.contains("duplicate key") && rootCauseMessage.contains("ordem_servico")) {
                message = "Esta Ordem de Serviço já foi utilizada anteriormente.";
            }
        }

        Map<String, String> errorResponse = Map.of("error", message);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


}