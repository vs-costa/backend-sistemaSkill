package br.com.api.backendapi.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

    //Tratamento erros específicos
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", exception.getMessage());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Recurso não encontrado");
        error.put("timestamp", formatDateTime(LocalDateTime.now()));
        error.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

        return error;
    }

    //Tratamento de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        errorDetails.put("message", "Erro de validação");
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetails.put("errors", fieldErrors);
        errorDetails.put("timestamp", formatDateTime(LocalDateTime.now()));
        errorDetails.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

        return errorDetails;
    }

    //Tratamentos genéricos
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleAllUncaughtException(Exception exception, WebRequest request) {
        Map<String, Object> error = new HashMap<>();

        error.put("message", "Ocorreu um erro interno no servidor");
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("erro", exception.getMessage());
        error.put("timestamp", formatDateTime(LocalDateTime.now()));
        error.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

        return error;
    }
}
